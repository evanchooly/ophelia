package controllers;

import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import models.ConnectionInfo;
import models.Query;
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
        return ok(views.html.index.render(generateContent()));
    }

    private static QueryResults generateContent() {
        QueryResults queryResults = new QueryResults();
        ConnectionInfo info = getConnectionInfo();
        List<String> names = MongOphelia.getDatabaseNames();
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
        Query query = form(Query.class).bindFromRequest().get();
        ConnectionInfo info = getConnectionInfo();
        if(query.bookmark != null && !"".equals(query.bookmark)) {
            query.save();
        }
        QueryResults queryResults = generateContent();
        try {
            info.setQuery(query);

            final Parser parser = new Parser(query.query);
            if(query.showCount) {
                Long count = parser.count(getDB());
                queryResults.setResultCount(count);
            }
            Object execute = new Parser(query.query).execute(getDB());
            if (execute instanceof DBCursor) {
                DBCursor dbResults = (DBCursor) execute;
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
            } else if (execute instanceof Number) {
                Map<String, Number> count = new TreeMap<>();
                count.put("count", (Number) execute);
                queryResults.setDbResults(Arrays.<Map>asList(count));
            }
        } catch (Exception e) {
            String message = e.getMessage();
            if(e.getCause() != null) {
                message += " " + e.getCause().getMessage();
            }
            queryResults.setError(message);
        }
        return ok(new JacksonMapper().valueToTree(queryResults));
    }

    private static DB getDB() {
        DB db = MongOphelia.get().getDB();
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
        info.query.save();
        info.save();
        session(INFO, info.getId().toString());
        return info;
    }

}