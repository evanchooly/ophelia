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

    parser = new Parser("db.UnitTest.find( { type : \"database\" } )");
    Iterator<BasicDBObject> iterator = (Iterator<BasicDBObject>) parser.execute(db);
    Assert.assertTrue(iterator.hasNext());
    Map map = iterator.next().toMap();
    Assert.assertEquals("database", map.get("type"));
    Assert.assertEquals(102, ((Map) map.get("info")).get("y"));
    String json = parser.getMapper().writer().writeValueAsString(map);
  }

  @Test
  public void parameters() throws IOException {
    Parser parser = new Parser("db.users.find({}, {thumbnail:0});");
    parser.execute(db);
  }

  @Test
  public void emptyFind() throws IOException {
    Parser parser = new Parser("db.Collection.find()");
    parser.execute(db);
  }

  @Test
  public void systemIndexes() throws IOException {
    Parser parser = new Parser("db.system.indexes.find()");
    parser.execute(db);
  }

  @Test
  public void objectId() throws IOException {
    String slug = "I'm just a random chunk of new ObjectId(\"4f54216718c69681f6f14e13\") text with"
      + " ObjectId(\"4f54216718c69681f6f14e13\") random references to ObjectId sprinkled in";
    String scrubbed = Parser.scrub(slug);

    Parser parser = new Parser("db.Collection.find( { _id : ObjectId(\"4f54216718c69681f6f14e13\") })");
    parser.execute(db);
  }
}
