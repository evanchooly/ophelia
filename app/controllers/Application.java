package controllers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.ArrayList;
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
    map.put("databaseList", getMongo().getDatabaseNames());
    return map;
  }

  private static Map<String, Object> loadCollections() throws UnknownHostException {
    TreeMap<String, Object> map = new TreeMap<>();
    String database = getDatabase();
    DB db = getDB();
    Set<String> collections = db.getCollectionNames();
    for (String collection : collections) {
      CommandResult stats = db.getCollection(collection).getStats();
      map.put(collection, stats.get("count"));
    }
    return map;
  }

  @Get("/database")
  public static void setDatabase(String database) throws UnknownHostException {
    if(database == null) {
      session.remove("database");
    } else {
      session.put("database", database);
    }
    renderJSON(generateContent());
  }
  @Post("/query")
  public static void query(String query) throws IOException {
    Mongo mongo = getMongo();
    Map<String, Object> content = generateContent();
    try {
      Parser parser = new Parser(query);
      DBCursor results = (DBCursor) parser.execute(getDB());
      if (results != null) {
        List<Map> list = new ArrayList<>();
        for (DBObject result : results) {
          list.add(result.toMap());
        }
        content.put("results", list);
      }
    } catch (InvalidQueryException e) {
      error(400, e.getMessage());
    }
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
      List<String> names = getMongo().getDatabaseNames();
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
    return new Mongo();
  }

  private static class GsonObjectIdJsonSerializer implements JsonSerializer<ObjectId> {
    @Override
    public JsonElement serialize(ObjectId o, Type type, JsonSerializationContext context) {
      return new JsonPrimitive(o.toString());
    }
  }
}