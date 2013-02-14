package com.antwerkz.ophelia.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.antwerkz.ophelia.models.ConnectionInfo;
import com.antwerkz.ophelia.models.Query;
import com.antwerkz.ophelia.plugins.MongOphelia;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.DB;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("app")
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    private static final String INFO = "connection-info";
    private JacksonMapper mapper = new JacksonMapper();

    private QueryResults generateContent(HttpSession session, QueryResults queryResults) {
        try {
            ConnectionInfo info = getConnectionInfo(session);
            String database = info.getDatabase();
            if (database == null) {
                database = MongOphelia.getDatabaseNames().get(0);
                info.setDatabase(database);
            }
            queryResults.setDatabaseList(MongOphelia.getDatabaseNames());
            queryResults.setBookmarks(loadBookmarks(database));
            queryResults.setInfo(info);
            queryResults.setCollections(loadCollections(info));
            return queryResults;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }
        return queryResults;
    }

    private List<Query> loadBookmarks(String database) {
        return Query.finder().findAll(database);
    }

    private Map<String, Object> loadCollections(final ConnectionInfo info) {
        TreeMap<String, Object> map = new TreeMap<>();
        DB db = getDB(info.getDatabase());
        if (db != null) {
            for (String collection : db.getCollectionNames()) {
                map.put(collection, db.getCollection(collection).getStats().get("count"));
            }
        }
        return map;
    }

    @GET
    @Path("content")
    @Produces(MediaType.APPLICATION_JSON)
    public QueryResults content(@Context HttpServletRequest request) {
        return generateContent(request.getSession(), new QueryResults());
    }

    @GET()
    @Path("/database/{database}")
    @Produces(MediaType.APPLICATION_JSON)
    public QueryResults database(@Context HttpServletRequest request, @PathParam("database") String database) {
        HttpSession session = request.getSession();
        getConnectionInfo(session).setDatabase(database);
        return generateContent(session, new QueryResults());
    }

    @GET()
    @Path("/host/{host}/{port}")
    @Produces(MediaType.APPLICATION_JSON)
    public QueryResults changeHost(@Context HttpServletRequest request, @PathParam("host") String dbHost,
        @PathParam("port") Integer dbPort) {
        ConnectionInfo info = getConnectionInfo(request.getSession());
        info.setHost(dbHost);
        info.setPort(dbPort);
        return content(request);
    }

    @POST
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public QueryResults query(@Context HttpServletRequest request, String json) throws IOException {
        QueryResults queryResults = new QueryResults();
        try {
            HttpSession session = request.getSession();
            ObjectNode node = (ObjectNode) mapper.readTree(json);
            Query query = mapper.convertValue(node, Query.class);
            String database = query.getDatabase();
            generateContent(session, queryResults);
            final Parser parser = new Parser(query);
            if (query.getShowCount()) {
                queryResults.setResultCount(parser.count(getDB(database)));
            }
            queryResults.setDbResults(parser.execute(getDB(database)));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            String message = e.getMessage();
            queryResults.setResultCount(null);
            queryResults.setDbResults(null);
            if (e.getCause() != null) {
                message += " " + e.getCause().getMessage();
            }
            queryResults.setError(message);
        }
        return queryResults;
    }

    @GET
    @Path("/deleteBookmark/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public QueryResults deleteBookmark(@Context HttpServletRequest request, @PathParam("id") String id) {
        QueryResults queryResults = new QueryResults();
        System.out.println("id = " + id);
        try {
            Query.finder().delete(new ObjectId(id));
        } catch (IllegalArgumentException e) {
            queryResults.setError(e.getMessage());
        }
        return generateContent(request.getSession(), queryResults);
    }

    private DB getDB(String database) {
        return MongOphelia.get(database).getDB();
    }

    public ConnectionInfo getConnectionInfo(HttpSession session) {
        ConnectionInfo info = (ConnectionInfo) session.getAttribute(INFO);
        if (info == null) {
            info = createConnection(session);
        }
        return info;
    }

    private static ConnectionInfo createConnection(HttpSession session) {
        ConnectionInfo info = new ConnectionInfo();
        session.setAttribute(INFO, info);
        return info;
    }
}