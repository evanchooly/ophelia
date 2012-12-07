package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
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

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import models.ConnectionInfo;
import models.Query;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import plugins.MongOphelia;

@Path("app")
public class Application {
  private static final Logger logger = LoggerFactory.getLogger(Application.class);
  private static final String INFO = "connection-info";
  public static Boolean authenticated = Boolean.FALSE;
  private JacksonMapper mapper = new JacksonMapper();

  private QueryResults generateContent(HttpSession session, QueryResults queryResults) {
    try {
      ConnectionInfo info = getConnectionInfo(session);
      List<String> names = MongOphelia.getDatabaseNames();
      queryResults.setDatabaseList(names);
      String database = getDatabase(session);
      if (database == null) {
        database = names.get(0);
        info.setDatabase(database);
      }
      queryResults.setBookmarks(loadBookmarks(null, database));
      queryResults.setInfo(info);
      queryResults.setCollections(loadCollections(session, info));
      return queryResults;
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      e.printStackTrace();
    }
    return queryResults;
  }

  private List<Query> loadBookmarks(HttpSession session, String database) {
    return Query.finder().findAll(database);
  }

  private Map<String, Object> loadCollections(HttpSession session, final ConnectionInfo info) {
    TreeMap<String, Object> map = new TreeMap<>();
    DB db = getDB(session, info.getDatabase());
    if (db != null) {
      for (String collection : db.getCollectionNames()) {
        CommandResult stats = db.getCollection(collection).getStats();
        map.put(collection, stats.get("count"));
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
      ConnectionInfo info = getConnectionInfo(session);
      ObjectNode node = (ObjectNode) mapper.readTree(json);
      Map<String, Object> treeMap = mapper.convertValue(node, TreeMap.class);
      info.setQueryString((String) treeMap.get("queryString"));
      info.setLimit((Integer) treeMap.get("limit"));
      info.setShowCount((Boolean) treeMap.get("showCount"));
      info.setDatabase((String) treeMap.get("database"));
      String bookmark = (String) treeMap.get("bookmark");
      String database = info.getDatabase();
      Map<String, String> params = Collections.<String, String>emptyMap();
      if (bookmark != null && !"".equals(bookmark)) {
        if (Query.finder().byBookmarkAndDatabase(bookmark, database) == null) {
          Query query = new Query();
          query.setQueryString(info.getQueryString());
          query.setBookmark(bookmark);
          query.setDatabase(info.getDatabase());
          query.save();
        } else {
          throw new RuntimeException("Bookmark already exists");
        }
      }
      generateContent(session, queryResults);
      final Parser parser = new Parser(info.getQueryString(), params);
      if (info.getShowCount()) {
        queryResults.setResultCount(parser.count(getDB(session, database)));
      }
      Object execute = parser.execute(getDB(session, database));
      if (execute instanceof DBCursor) {
        DBCursor dbResults = (DBCursor) execute;
        List<Map> list = new ArrayList<>();
        Iterator<DBObject> iterator = dbResults.iterator();
        while (list.size() < info.getLimit() && iterator.hasNext()) {
          list.add(iterator.next().toMap());
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

  private DB getDB(HttpSession session, String database) {
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