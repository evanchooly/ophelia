package controllers;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.Mongo;
import play.modules.router.Post;
import play.mvc.Controller;

public class Application extends Controller {
  public static void index() {
    render();
  }

  @Post("/query")
  public static void query(String query) throws UnknownHostException {
    Mongo mongo = new Mongo();
    DB db = mongo.getDB("collage");
    Object list = db.eval(query);
    index();
  }
}