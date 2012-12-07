package com.antwerkz.ophelia;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.Mongo;
import controllers.Parser;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class ParserTest {
  private DB db;

  public ParserTest() throws UnknownHostException {
    db = new Mongo().getDB("collage");
  }

  @Test
  public void insert() throws IOException {
    Parser parser = new Parser("db.UnitTest.insert({"
      + "\"name\" : \"MongoDB\","
      + "\"type\" : \"database\","
      + "\"count\" : 1,"
      + "\"info\" : {\"x\" : 203,"
      + "\"y\" : 102}})", Collections.<String, String>emptyMap());
    parser.execute(db);
    parser = new Parser("db.UnitTest.find( { type : \"database\" } )", Collections.<String, String>emptyMap());
    Iterator<BasicDBObject> iterator = (Iterator<BasicDBObject>) parser.execute(db);
    Assert.assertTrue(iterator.hasNext());
    Map map = iterator.next().toMap();
    Assert.assertEquals(map.get("type"), "database");
    Assert.assertEquals(((Map) map.get("info")).get("y"), 102);
    String json = parser.getMapper().writer().writeValueAsString(map);
  }

  @Test
  public void parameters() throws IOException {
    Parser parser = new Parser("db.users.find({}, {thumbnail:0});", Collections.<String, String>emptyMap());
    parser.execute(db);
  }

  @Test
  public void emptyFind() throws IOException {
    Parser parser = new Parser("db.Collection.find()", Collections.<String, String>emptyMap());
    parser.execute(db);
  }

  @Test
  public void systemIndexes() throws IOException {
    Parser parser = new Parser("db.system.indexes.find()", Collections.<String, String>emptyMap());
    parser.execute(db);
  }

  @Test
  public void objectId() throws IOException {
    Parser parser = new Parser("db.Collection.find( { _id : ObjectId(\"4f54216718c69681f6f14e13\") })",
      Collections.<String, String>emptyMap());
    parser.execute(db);
  }

  public void parameterized() throws IOException {
    Map<String, String> params = new HashMap<>();
    params.put("id", "i'm an ID");
    params.put("name", "and I'm a name!");
    Parser parser = new Parser("db.ConnectedAccounts.find( {\n"
      + "  _id : \"{{id}}\",\n"
      + "  prop : \"{{id}}\",\n"
      + "  name : \"{{name}}\"\n"
      + "} )", params);
    parser.execute(db);
  }
  /*
      @Test
      public void like() throws IOException {
          Jongo jongo = new Jongo(db);
          jongo.getCollection("bob").find("{ name : /like this/ }");
          Parser parser = new Parser("db.Collection.find( { name : /something like this/ } )");
          parser.execute(db);
      }
  */


}
