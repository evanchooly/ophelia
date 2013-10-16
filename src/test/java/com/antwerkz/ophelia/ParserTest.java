/**
 * Copyright (C) 2012-2013 Justin Lee <jlee@antwerkz.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.antwerkz.ophelia;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.antwerkz.ophelia.controllers.JacksonMapper;
import com.antwerkz.ophelia.controllers.Parser;
import com.antwerkz.ophelia.models.MongoCommand;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class ParserTest {
  private static final String DATE_PATTERN = "yyyy-MM-dd hh:mm:ss";

  private DB db;

  public ParserTest() throws UnknownHostException {
    db = new MongoClient().getDB("testing");
  }

  @Test
  public void insert() throws IOException {
    db.getCollection("UnitTest").drop();
    MongoCommand command = new MongoCommand("db.UnitTest.insert({"
        + "\"name\" : \"MongoDB\","
        + "\"type\" : \"database\","
        + "\"count\" : 1,"
        + "\"date\" : \"{{now}}\","
        + "\"info\" : {\"x\" : 203,"
        + "\"y\" : 102}})");
    final Map<String, String> params = new HashMap<>();
    String date = DateTime.now().toString(DATE_PATTERN);
    params.put("now", date);
    command.setParams(params);
    Parser parser = new Parser(command);
    parser.execute(db);
    parser = new Parser(new MongoCommand("db.UnitTest.find( { type : \"database\" } )"));
    List<Map> execute = parser.execute(db);
    Iterator<Map> iterator = execute.iterator();
    Map map = iterator.next();
    Assert.assertEquals(map.get("type"), "database");
    Assert.assertEquals(map.get("date"), date);
    Assert.assertEquals(((Map) map.get("info")).get("y"), 102);
    parser.getMapper().writer().writeValueAsString(map);
  }

  @Test
  public void fields() throws IOException {
    DBCollection collection = db.getCollection("fields");
    collection.drop();
    List<DBObject> list = new ArrayList<>();
    for (int x = 0; x < 10; x++) {
      BasicDBObject objects = new BasicDBObject();
      objects.put("_id", new ObjectId());
      objects.put("bobby", "mcgee");
      objects.put("count", x);
      list.add(objects);
    }
    collection.insert(list);
    Parser parser = new Parser(new MongoCommand("db.users.find({}, {count:0});"));
    List<Map> results = parser.execute(db);
    for (Map result : results) {
      Assert.assertNull(result.get("count"));
    }
  }

  @Test
  public void emptyFind() throws IOException {
    Parser parser = new Parser(new MongoCommand("db.collection.find()"));
    parser.execute(db);
  }

  @Test
  public void systemIndexes() throws IOException {
    Parser parser = new Parser(new MongoCommand("db.system.indexes.find()"));
    parser.execute(db);
  }

  @Test
  public void objectId() throws IOException {
    Parser parser = new Parser(new MongoCommand("db.Collection.find( { _id : ObjectId(\"4f54216718c69681f6f14e13\") })")
    );
    parser.execute(db);
  }

  public void parameterized() throws IOException {
    Map<String, String> params = new HashMap<>();
    params.put("id", "i'm an ID");
    params.put("name", "and I'm a name!");
    MongoCommand mongoCommand = new MongoCommand("db.ConnectedAccounts.find( {\n"
        + "  _id : \"{{id}}\",\n"
        + "  prop : \"{{id}}\",\n"
        + "  name : \"{{name}}\"\n"
        + "} )");
    mongoCommand.setParams(params);
    new Parser(mongoCommand).execute(db);
  }

  @Test
  public void explain() throws IOException {
    MongoCommand mongoCommand = new MongoCommand("db.UnitTest.insert({"
        + "\"name\" : \"MongoDB\","
        + "\"type\" : \"database\","
        + "\"count\" : 1,"
        + "\"info\" : {\"x\" : 203,"
        + "\"y\" : 102}})");
    Parser parser = new Parser(mongoCommand);
    Assert.assertFalse(parser.explain(db).get(0).isEmpty());
  }

  @Test
  public void export() throws IOException {
    MongoCommand mongoCommand = new MongoCommand("db.UnitTest.insert({"
        + "\"name\" : \"MongoDB\","
        + "\"type\" : \"database\","
        + "\"count\" : 1,"
        + "\"info\" : {\"x\" : 203,"
        + "\"y\" : 102}})");
    Parser parser = new Parser(mongoCommand);
    parser.setLimit(null);
    Assert.assertFalse(parser.execute(db).get(0).isEmpty());
  }

  @Test
  public void serialize() throws IOException {
    JacksonMapper mapper = new JacksonMapper();
    String json = "{\"bookmark\":\"\",\"queryString\":\"db.ConnectedAccounts.find( {\\n\\n} )\",\"limit\":100,"
        + "\"showCount\":true,\"params\":{},\"database\":\"_test\"}";
    ObjectNode node = (ObjectNode) mapper.readTree(json);
    MongoCommand mongoCommand = mapper.convertValue(node, MongoCommand.class);
    Assert.assertTrue(mongoCommand.getShowCount());
  }

  @Test
  public void limits() throws IOException {
    DBCollection collection = db.getCollection("limits");
    collection.drop();
    List<DBObject> list = new ArrayList<>();
    for (int x = 0; x < 100; x++) {
      BasicDBObject objects = new BasicDBObject();
      objects.put("_id", new ObjectId());
      objects.put("bobby", "mcgee");
      objects.put("count", x);
      list.add(objects);
    }
    collection.insert(list);
    MongoCommand mongoCommand = new MongoCommand(
        "db.limits.find({ \"bobby\" : { $ne : \"george\" }}).sort({\"count\" : -1})");
    mongoCommand.setLimit(5);
    Assert.assertEquals(new Parser(mongoCommand).execute(db).size(), 5);
    mongoCommand.setLimit(6000);
    Assert.assertEquals(new Parser(mongoCommand).execute(db).size(), MongoCommand.DEFAULT_LIMIT);
    Assert.assertEquals(new Parser(new MongoCommand("db.limits.find({ \"bobby\" : { $ne : \"george\" }})"
        + ".sort({\"count\" : -1}).limit(10)")).execute(db).size(), 10);
  }
}