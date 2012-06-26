package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import play.modules.router.Post;
import play.mvc.Controller;

public class Application extends Controller {
  public static void index() {
    render();
  }

  @Post("/query")
  public static void query(String query) throws IOException {
    Mongo mongo = new Mongo();
    List<Map> list = new ArrayList<Map>();
    try {
      Parser parser = new Parser(query);
      DBCursor results = (DBCursor) parser.execute(mongo.getDB("collage"));
      for (DBObject result : results) {
        list.add(result.toMap());
      }
    } catch (InvalidQueryException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
    renderJSON(list);
  }
}