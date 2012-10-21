package controllers;

import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import models.ConnectionInfo;
import models.Query;
import org.bson.types.ObjectId;
import plugins.MongOphelia;

import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Path("/")
public class Application {
    private static final String INFO = "connection-info";
    public static final String SESSION_KEY = "session-key";
    public static Boolean authenticated = Boolean.FALSE;

    private QueryResults generateContent(HttpSession session) {
        QueryResults queryResults = new QueryResults();
        ConnectionInfo info = getConnectionInfo(session);
        List<String> names = MongOphelia.getDatabaseNames();
        queryResults.setDatabaseList(names);
        String database = getDatabase(session);
        if (database == null) {
            database = names.get(0);
            info.setDatabase(database);
        }
        queryResults.setInfo(info);
        queryResults.setCollections(loadCollections(session, info));
        return queryResults;
    }

    private Map<String, Object> loadCollections(HttpSession session, final ConnectionInfo info) {
        TreeMap<String, Object> map = new TreeMap<>();
        DB db = getDB(info.getDatabase(), session);
        if (db != null) {
            for (String collection : db.getCollectionNames()) {
                CommandResult stats = db.getCollection(collection).getStats();
                map.put(collection, stats.get("count"));
            }
        }
        return map;
    }

    @GET()
    @Path("/content")
    @Produces("application/json")
    public QueryResults getContent(@Context HttpSession session) {
        return generateContent(session);
    }

    @GET()
    @Path("/database")
    @Produces("application/json")
    public QueryResults database(@Context HttpSession session, String database) {
        getConnectionInfo(session).setDatabase(database);
        return generateContent(session);
    }

    @GET()
    @Path("/host")
    @Produces("application/json")
    public QueryResults changeHost(@Context HttpSession session, String dbHost, Integer dbPort) {
        ConnectionInfo info = getConnectionInfo(session);
        info.setHost(dbHost);
        info.setPort(dbPort);
        return getContent(session);
    }

    @POST()
    @Path("/query")
    @Produces("application/json")
    public QueryResults query(@Context HttpSession session, Query query) throws IOException {
        QueryResults queryResults;
        try {
            ConnectionInfo info = getConnectionInfo(session);
            if (query.bookmark != null && !"".equals(query.bookmark)) {
                Query saved = Query.find().byBookmark(query.bookmark);
                if (saved != null || saved.equals(query)) {
                    query.save();
                    info.queryId = query.getId();
                } else {
                    throw new RuntimeException("Bookmark already exists");
                }
            }
            info.setQueryString(query.queryString);
            queryResults = generateContent(session);
            info.setQueryString(query.queryString);

            final Parser parser = new Parser(query.queryString);
            if (info.showCount) {
                Long count = parser.count(getDB(info.getDatabase(), session));
                queryResults.setResultCount(count);
            }
            Object execute = new Parser(query.queryString).execute(getDB(info.getDatabase(), session));
            if (execute instanceof DBCursor) {
                DBCursor dbResults = (DBCursor) execute;
                List<Map> list = new ArrayList<>();
                Iterator<DBObject> iterator = dbResults.iterator();
                while (list.size() < info.getLimit() && iterator.hasNext()) {
                    DBObject result = iterator.next();
                    list.add(result.toMap());
                }
                if (list.isEmpty()) {
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
            queryResults = new QueryResults();
            String message = e.getMessage();
            if (e.getCause() != null) {
                message += " " + e.getCause().getMessage();
            }
            queryResults.setError(message);
        }
        return queryResults;
    }

    private DB getDB(String database, HttpSession session) {
        DB db = MongOphelia.get(database).getDB();
        db.setReadOnly(getConnectionInfo(session).getReadOnly());
        return db;
    }

    private String getDatabase(HttpSession session) {
        return getConnectionInfo(session).getDatabase();
    }

    public ConnectionInfo getConnectionInfo(HttpSession session) {
        String id = (String) session.getAttribute(INFO);
        ConnectionInfo info;
        if (id == null) {
            info = createConnection(session);
        } else {
            info = ConnectionInfo.find().byId(new ObjectId(id));
            if (info == null) {
                info = createConnection(session);
            }
        }
        return info;
    }

    private static ConnectionInfo createConnection(HttpSession session) {
        ConnectionInfo info = new ConnectionInfo();
        info.save();
        session.setAttribute(INFO, info.getId().toString());
        return info;
    }

}