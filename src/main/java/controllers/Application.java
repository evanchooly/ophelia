package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
  public static final String SESSION_KEY = "session-key";
  public static Boolean authenticated = Boolean.FALSE;
  private JacksonMapper mapper = new JacksonMapper();

  private QueryResults generateContent(HttpSession session) {
    try {
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
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new QueryResults();
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
    System.out.println("Application.content");
    return generateContent(request.getSession());
  }

  @GET()
  @Path("/database/{database}")
  @Produces(MediaType.APPLICATION_JSON)
  public QueryResults database(@Context HttpServletRequest request, @PathParam("database") String database) {
    HttpSession session = request.getSession();
    getConnectionInfo(session).setDatabase(database);
    return generateContent(session);
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
  //  @Consumes(MediaType.APPLICATION_JSON)
  public QueryResults query(@Context HttpServletRequest request, String json) throws IOException {
    QueryResults queryResults;
    try {
      ObjectNode node = (ObjectNode) mapper.readTree(json);
      @SuppressWarnings("unchecked") Map<String, Object> treeMap = mapper.convertValue(node, TreeMap.class);
      String queryString = (String) treeMap.get("queryString");
      Integer limit = (Integer) treeMap.get("limit");
      Boolean showCount = (Boolean) treeMap.get("showCount");
      String bookmark = (String) treeMap.get("bookmark");
      HttpSession session = request.getSession();
      ConnectionInfo info = getConnectionInfo(session);
      if (bookmark != null && !"".equals(bookmark)) {
        Query saved = Query.find().byBookmark(bookmark);
        if (saved != null) {
          Query query = new Query();
          query.setQueryString(queryString);
          query.setBookmark(bookmark);
          query.save();
        } else {
          throw new RuntimeException("Bookmark already exists");
        }
      }
      info.setQueryString(queryString);
      queryResults = generateContent(session);
      final Parser parser = new Parser(queryString);
      if (info.getShowCount()) {
        Long count = parser.count(getDB(session, info.getDatabase()));
        queryResults.setResultCount(count);
      }
      Object execute = parser.execute(getDB(session, info.getDatabase()));
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
      logger.error(e.getMessage(), e);
      queryResults = new QueryResults();
      String message = e.getMessage();
      if (e.getCause() != null) {
        message += " " + e.getCause().getMessage();
      }
      queryResults.setError(message);
    }
    return queryResults;
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