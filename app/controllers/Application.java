package controllers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import models.ConnectionInfo;
import models.Results;
import org.bson.types.ObjectId;
import play.modules.router.Get;
import play.modules.router.Post;
import play.mvc.Controller;

public class Application extends Controller {
  private static final String CONTEXT_NAME = "-context";

  public static void index() throws UnknownHostException {
    Results content = new Results(); //generateContent();
    render(content);
  }

  private static Results generateContent() throws UnknownHostException {
    Results results = new Results();
    results.setCollections(loadCollections());
    results.setDatabase(getDatabase());
    results.setDatabaseList(getMongo().getDatabaseNames());
    return results;
  }

  private static Map<String, Object> loadCollections() throws UnknownHostException {
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

  @Get("/content")
  public static void getContent() throws UnknownHostException {
    renderJSON(generateContent());
  }

  @Get("/database")
  public static void setDatabase(String database) throws UnknownHostException {
    getConnectionInfo().setDatabase(database);
    renderJSON(generateContent());
  }

  @Post("/changeHost")
  public static void changeHost(String dbHost, Integer dbPort) throws UnknownHostException {
    ConnectionInfo info = getConnectionInfo();
    info.setHost(dbHost);
    info.setPort(dbPort);
    index();
  }

  @Post("/query")
  public static void query(String query) throws IOException {
    ConnectionInfo info = getConnectionInfo();
    info.setQuery(query);
    Results results = generateContent();
    try {
      Parser parser = new Parser(query);
      Object execute = parser.execute(getDB());
      if (execute instanceof DBCursor) {
        DBCursor dbResults = (DBCursor) execute;
        if (dbResults != null) {
          List<Map> list = new ArrayList<>();
          for (DBObject result : dbResults) {
            list.add(result.toMap());
          }
          results.setDbResults(list);
        }
      } else if (execute instanceof Number) {
        Map<String, Number> count = new TreeMap<>();
        count.put("count", (Number) execute);
        results.setDbResults(Arrays.<Map>asList(count));
      }
    } catch (InvalidQueryException e) {
      results.setError(e.getMessage());
    }
    renderJSON(results, new GsonObjectIdJsonSerializer());
  }

  private static DB getDB() throws UnknownHostException {
    DB db = getMongo().getDB(getDatabase());
    db.setReadOnly(getConnectionInfo().getReadOnly());
    return db;
  }

  private static String getDatabase() throws UnknownHostException {
    return getConnectionInfo().getDatabase();
  }

  private static Mongo getMongo() throws UnknownHostException {
    ConnectionInfo info = getConnectionInfo();
    return new Mongo(info.getHost(), info.getPort());
  }

  public static ConnectionInfo getConnectionInfo() {
    ConnectionInfo info = ConnectionInfo.find("bySession", session.getId()).first();
    if(info == null) {
      System.out.println("** creating a new info");
      info = new ConnectionInfo(session.getId());
      info.save();
    }
    return info;
  }
  
  private static class GsonObjectIdJsonSerializer implements JsonSerializer<ObjectId> {
    @Override
    public JsonElement serialize(ObjectId o, Type type, JsonSerializationContext context) {
      return new JsonPrimitive(o.toString());
    }
  }
}