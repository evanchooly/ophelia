import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.Mongo;
import controllers.Parser;
import org.junit.Assert;
import org.junit.Test;
import play.test.FunctionalTest;

public class ParserTest extends FunctionalTest {
  private Mongo mongo;
  private DB db;

  public ParserTest() throws UnknownHostException {
    mongo = new Mongo();
    db = mongo.getDB("collage");
  }

  @Test
  public void insert() throws IOException {
    Parser parser = new Parser("db.UnitTest.insert({"
      + "\"name\" : \"MongoDB\","
      + "\"type\" : \"database\","
      + "\"count\" : 1,"
      + "\"info\" : {\"x\" : 203,"
      + "\"y\" : 102}})");
    Object execute = parser.execute(db);

    parser = new Parser("db.UnitTest.find( { type : \"database\" }");
    Iterator<BasicDBObject> iterator = (Iterator<BasicDBObject>) parser.execute(db);
    Assert.assertTrue(iterator.hasNext());
    Map map = iterator.next().toMap();
    String json = parser.getMapper().writer().writeValueAsString(map);
  }

  @Test
  public void emptyFind() throws IOException {
    Parser parser = new Parser("db.Collection.find()");
    parser.execute(db);
  }
}
