import org.junit.Test;
import play.mvc.Http.Request;
import play.mvc.Http.Response;
import play.test.FunctionalTest;

public class QueryTest extends FunctionalTest {
  @Test
  public void inserts() {
    Request request = play.test.FunctionalTest.newRequest();
    request.params.put("query", "db.TestCollection.insert( {"
      + "   \"name\" : \"MongoDB\","
      + "   \"type\" : \"database\","
      + "   \"count\" : 1,"
      + "   \"info\" : {"
      + "               x : 203,"
      + "               y : 102"
      + "             }"
      + "})");

    Response response = POST(request, "/query");
  }

}
