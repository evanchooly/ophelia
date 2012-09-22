package controllers;

import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import models.ConnectionInfo;
import models.Query;
import models.QueryResults;
import org.bson.types.ObjectId;
import play.mvc.Controller;
import play.mvc.Result;
import plugins.MongOphelia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Application extends Controller {
    private static final String INFO = "connection-info";

    public static Result index() {
        System.out.println("Application.index");
        return ok(views.html.index.render(generateContent()));
    }

    private static QueryResults generateContent() {
        QueryResults queryResults = new QueryResults();
        ConnectionInfo info = getConnectionInfo();
        List<String> names = MongOphelia.get().getDatabaseNames();
        queryResults.setDatabaseList(names);
        String database = getDatabase();
        if (database == null) {
            database = names.get(0);
            info.setDatabase(database);
        }
        queryResults.setInfo(info);
        queryResults.setCollections(loadCollections());
        return queryResults;
    }

    private static Map<String, Object> loadCollections() {
        TreeMap<String, Object> map = new TreeMap<>();
        DB db = getDB();
        if (db != null) {
            for (String collection : db.getCollectionNames()) {
                CommandResult stats = db.getCollection(collection).getStats();
                map.put(collection, stats.get("count"));
            }
        }
        return map;
    }

    public static Result getContent() {
        return ok(new JacksonMapper().valueToTree(generateContent()));
    }

    public static Result database(String database) {
        getConnectionInfo().setDatabase(database);
        return ok(new JacksonMapper().valueToTree(generateContent()));
    }

    public static Result changeHost(String dbHost, Integer dbPort) {
        ConnectionInfo info = getConnectionInfo();
        info.setHost(dbHost);
        info.setPort(dbPort);
        return index();
    }

    public static Result query() throws IOException {
        ConnectionInfo info = getConnectionInfo();
        Query query = form(Query.class).bindFromRequest().get();
        info.setQuery(query);

        QueryResults queryResults = generateContent();
        try {
            Object execute = new Parser(query.query).execute(getDB());
            if (execute instanceof DBCursor) {
                DBCursor dbResults = (DBCursor) execute;
                if (dbResults != null) {
                    List<Map> list = new ArrayList<>();
                    Iterator<DBObject> iterator = dbResults.iterator();
                    while (list.size() < info.query.getLimit() && iterator.hasNext()) {
                        DBObject result = iterator.next();
                        list.add(result.toMap());
                    }
                    if(list.isEmpty()) {
                        Map<String, String> map = new TreeMap<>();
                        map.put("message", "No results found");
                        list.add(map);
                    }
                    queryResults.setDbResults(list);
                }
            } else if (execute instanceof Number) {
                Map<String, Number> count = new TreeMap<>();
                count.put("count", (Number) execute);
                queryResults.setDbResults(Arrays.<Map>asList(count));
            }
        } catch (InvalidQueryException e) {
            queryResults.setError(e.getMessage());
        }
        return ok(new JacksonMapper().valueToTree(queryResults));
    }

    private static DB getDB() {
        DB db = MongOphelia.get().getDB(getDatabase());
        db.setReadOnly(getConnectionInfo().query.getReadOnly());
        return db;
    }

    private static String getDatabase() {
        return getConnectionInfo().getDatabase();
    }

    public static ConnectionInfo getConnectionInfo() {
        String id = session(INFO);
        ConnectionInfo info;
        if (id == null) {
            info = createConnection();
        } else {
            info = ConnectionInfo.find.byId(new ObjectId(id));
            if (info == null) {
                info = createConnection();
            }
        }
        return info;
    }

    private static ConnectionInfo createConnection() {
        ConnectionInfo info = new ConnectionInfo();
        info.save();
        session(INFO, info.getId().toString());
        return info;
    }

}