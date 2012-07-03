package controllers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import org.bson.types.ObjectId;
import play.modules.router.Post;
import play.mvc.Controller;

public class Application extends Controller {
  public static void index() {
    render();
  }

  @Post("/query")
  public static void query(String query) throws IOException {
    Mongo mongo = new Mongo();
    Map<String, Object> map = new HashMap<>();
    try {
      Parser parser = new Parser(query);
      DBCursor results = (DBCursor) parser.execute(mongo.getDB("collage"));
      if (results != null) {
        List<Map> list = new ArrayList<>();
        for (DBObject result : results) {
          list.add(result.toMap());
        }
        map.put("results", list);
      }
    } catch (InvalidQueryException e) {
      error(400, e.getMessage());
    }
    renderJSON(map, new GsonObjectIdJsonSerializer());
  }

  private static class GsonObjectIdJsonSerializer implements JsonSerializer<ObjectId> {
    @Override
    public JsonElement serialize(ObjectId o, Type type, JsonSerializationContext context) {
      return new JsonPrimitive(o.toString());
    }
  }
}