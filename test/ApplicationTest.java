import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import play.mvc.Http.Response;
import play.test.FunctionalTest;

public class ApplicationTest extends FunctionalTest {
  @Test
  public void query() {
    Map<String, String> params = new HashMap<String, String>();
    params.put("query", "db.Messages.find();");
    Response response = POST("/query", params);
    System.out.println("response = " + response.contentType);
    assertIsOk(response);
    assertContentType("application/json", response);
    assertCharset(play.Play.defaultWebEncoding, response);
  }

  @Test
  public void badQuery() {
    Map<String, String> params = new HashMap<String, String>();
    params.put("query", "bob");
    Response response = POST("/query", params);
    System.out.println("response = " + response.contentType);
    assertIsOk(response);
    assertContentType("application/json", response);
    assertCharset(play.Play.defaultWebEncoding, response);
  }
}