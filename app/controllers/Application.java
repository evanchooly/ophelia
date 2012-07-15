package controllers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import org.bson.types.ObjectId;
import play.modules.router.Get;
import play.modules.router.Post;
import play.mvc.Controller;

public class Application extends Controller {
  public static void index() throws UnknownHostException {
    Map content = generateContent();
    render(content);
  }

  private static Map<String, Object> generateContent() throws UnknownHostException {
    Map<String, Object> map = new HashMap<>();
    map.put("collections", loadCollections());
    map.put("database", getDatabase());
    Mongo mongo = getMongo();
    map.put("databaseList", mongo != null ? mongo.getDatabaseNames() : Collections.emptyList());
    return map;
  }

  private static Map<String, Object> loadCollections() throws UnknownHostException {
    TreeMap<String, Object> map = new TreeMap<>();
    String database = getDatabase();
    DB db = getDB();
    if (db != null) {
      Set<String> collections = db.getCollectionNames();
      for (String collection : collections) {
        CommandResult stats = db.getCollection(collection).getStats();
        map.put(collection, stats.get("count"));
      }
    }
    return map;
  }

  @Get("/database")
  public static void setDatabase(String database) throws UnknownHostException {
    if (database == null) {
      session.remove("database");
    } else {
      session.put("database", database);
    }
    renderJSON(generateContent());
  }

  @Post("/changeHost")
  public static void changeHost(String dbHost, String dbPort)
    throws UnknownHostException {
    session.put("dbHost", dbHost);
    session.put("dbPort", dbPort);
    index();
  }

  @Post("/query")
  public static void query(String query) throws IOException {
    Map<String, Object> content = new TreeMap<>();
    try {
      Parser parser = new Parser(query);
      Object execute = parser.execute(getDB());
      if (execute instanceof DBCursor) {
        DBCursor results = (DBCursor) execute;
        if (results != null) {
          List<Map> list = new ArrayList<>();
          for (DBObject result : results) {
            list.add(result.toMap());
          }
          content.put("results", list);
        }
      } else if (execute instanceof Number) {
        Map<String, Number> count = new TreeMap<>();
        count.put("count", (Number) execute);
        List<Map> list = Arrays.<Map>asList(count);
        content.put("results", list);
      }
    } catch (InvalidQueryException e) {
      error(400, e.getMessage());
    }
    content.putAll(generateContent());
    renderJSON(content, new GsonObjectIdJsonSerializer());
  }

  private static DB getDB() throws UnknownHostException {
    DB db = getMongo().getDB(getDatabase());
    String readOnly = params.get("readOnly");
    db.setReadOnly(Boolean.valueOf(readOnly));
    return db;
  }

  private static String getDatabase() throws UnknownHostException {
    String database = session.get("database");
    if (database == null) {
      Mongo mongo = getMongo();
      List<String> names = mongo != null ? mongo.getDatabaseNames() : Collections.<String>emptyList();
      if (!names.isEmpty()) {
        database = names.get(0);
      } else {
        database = "local";
      }
      session.put("database", database);
    }
    return database;
  }

  private static Mongo getMongo() throws UnknownHostException {
    String dbHost = session.get("dbHost");
    if (dbHost == null) {
      dbHost = "localhost";
      session.put("dbHost", dbHost);
    }
    String dbPort = session.get("dbPort");
    if (dbPort == null) {
      dbPort = "27017";
      session.put("dbPort", dbPort);
    }
    return new Mongo(dbHost, Integer.parseInt(dbPort));
  }

  private static class GsonObjectIdJsonSerializer implements JsonSerializer<ObjectId> {
    @Override
    public JsonElement serialize(ObjectId o, Type type, JsonSerializationContext context) {
      return new JsonPrimitive(o.toString());
    }
  }
}