package controllers;

import play.modules.router.Post;
import play.mvc.Controller;

public class Application extends Controller {
  public static void index() {
    render();
  }

  @Post("/query")
  public static void query(String query) {
    index();
  }
}