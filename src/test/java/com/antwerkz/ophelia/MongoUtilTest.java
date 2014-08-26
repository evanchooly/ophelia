/**
 * Copyright (C) 2012-2014 Justin Lee <jlee@antwerkz.com>
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

import com.antwerkz.ophelia.models.MongoCommand;
import com.antwerkz.ophelia.utils.MongoUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import org.bson.types.ObjectId;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.antwerkz.ophelia.models.MongoCommand.insert;
import static com.antwerkz.ophelia.models.MongoCommand.query;
import static com.antwerkz.ophelia.models.MongoCommand.remove;
import static com.antwerkz.ophelia.models.MongoCommand.update;
import static java.lang.String.format;

@Test
public class MongoUtilTest {
    public static final String DATABASE_NAME = "testing";
    public static final String COLLECTION_NAME = "UnitTest";

    private DB db;
    private MongoUtil mongoUtil;

    public MongoUtilTest() throws UnknownHostException {
        final MongoClient client = new MongoClient();
        db = client.getDB(DATABASE_NAME);
        mongoUtil = new MongoUtil(client);
    }

    @Test
    public void insertDocument() throws IOException {
        db.getCollection(COLLECTION_NAME).drop();

        mongoUtil.insert(insert("{name : \"John Doe\", \"age\" : 23 }")
                             .namespace(DATABASE_NAME, COLLECTION_NAME));

        MongoCommand query = query("{}")
                                 .namespace(DATABASE_NAME, COLLECTION_NAME);

        Iterator<Map> iterator = mongoUtil.query(query).iterator();
        Map map = iterator.next();
        Assert.assertEquals(map.get("name"), "John Doe");
        Assert.assertEquals(map.get("age"), 23);
    }

    @Test
    public void fields() throws IOException {
        generateData();
        MongoCommand query = query("")
                                 .setProjections("{count:0}")
                                 .namespace(DATABASE_NAME, COLLECTION_NAME);

        List<Map> results = mongoUtil.query(query);
        for (Map result : results) {
            Assert.assertNull(result.get("count"));
        }
    }

    @Test
    public void queryWithASort() throws IOException {
        generateData();
        MongoCommand query = query("")
                                 .namespace(DATABASE_NAME, COLLECTION_NAME);

        Assert.assertEquals(mongoUtil.query(query).iterator().next().get("count"), 0);

        query.setSort("{count:-1}");
        Assert.assertEquals(mongoUtil.query(query).iterator().next().get("count"), 9);
    }

    @Test
    public void queryWithALimit() throws IOException {
        generateData();
        MongoCommand query = query("")
                                 .setLimit(5)
                                 .namespace(DATABASE_NAME, COLLECTION_NAME);

        Assert.assertEquals(mongoUtil.query(query).size(), 5);

    }

    @Test
    public void simpleUpdate() {
        DBCollection collection = db.getCollection(COLLECTION_NAME);
        collection.drop();

        MongoCommand command = insert("{ name : \"John Doe\", age: 30 }")
                                   .namespace(DATABASE_NAME, COLLECTION_NAME);
        mongoUtil.insert(command);

        String query = "{ name : \"John Doe\" }";
        command = update(query, "{ $inc : { age : 1 } }")
                      .setMultiple(false)
                      .setUpsert(false)
                      .namespace(DATABASE_NAME, COLLECTION_NAME);

        mongoUtil.update(command);

        List<Map> result = mongoUtil.query(query(query).namespace(DATABASE_NAME, COLLECTION_NAME));
        Assert.assertEquals(result.get(0).get("age"), 31);
    }

    @Test
    public void simpleRemove() {
        DBCollection collection = db.getCollection(COLLECTION_NAME);
        collection.drop();

        MongoCommand command = insert("{ name : \"John Doe\", age: 30 }")
                                   .namespace(DATABASE_NAME, COLLECTION_NAME);
        mongoUtil.insert(command);

        String query = "{ name : \"John Doe\" }";
        command = remove(query)
                      .setMultiple(false)
                      .setUpsert(false)
                      .namespace(DATABASE_NAME, COLLECTION_NAME);

        mongoUtil.remove(command);

        List<Map> result = mongoUtil.query(query(query).namespace(DATABASE_NAME, COLLECTION_NAME));
        Assert.assertEquals(result.get(0).get("age"), 31);
    }

    private void generateData() {
        DBCollection collection = db.getCollection(COLLECTION_NAME);
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
    }

    @Test
    public void emptyFind() {
        db.getCollection(COLLECTION_NAME).drop();

        mongoUtil.insert(insert("{name : \"John Doe\", \"age\" : 23 }")
                             .namespace(DATABASE_NAME, COLLECTION_NAME));

        MongoCommand query = query("")
                                 .namespace(DATABASE_NAME, COLLECTION_NAME);
        Iterator<Map> iterator = mongoUtil.query(query).iterator();

        Map map = iterator.next();
        Assert.assertEquals(map.get("name"), "John Doe");
        Assert.assertEquals(map.get("age"), 23);
    }

    @Test
    public void systemIndexes() throws IOException {
        MongoCommand query = query("")
                                 .namespace(DATABASE_NAME, "system.indexes");
        Iterator<Map> iterator = mongoUtil.query(query).iterator();
        Assert.assertTrue(iterator.hasNext());
    }

    @Test
    public void objectId() throws IOException {
        db.getCollection(COLLECTION_NAME).drop();

        mongoUtil.insert(insert("{name : \"John Doe\", \"age\" : 23 }")
                             .namespace(DATABASE_NAME, COLLECTION_NAME));

        Map map = mongoUtil.query(query("{}").namespace(DATABASE_NAME, COLLECTION_NAME))
                           .iterator().next();

        Map loaded = mongoUtil.query(query(format("{ _id : ObjectId(\"%s\") }", map.get("_id").toString()))
                                         .namespace(DATABASE_NAME, COLLECTION_NAME))
                              .iterator().next();
        Assert.assertEquals(map, loaded);
    }

    /*
        public void parameterized() throws IOException {
            Map<String, String> params = new HashMap<>();
            params.put("id", "i'm an ID");
            params.put("name", "and I'm a name!");
            MongoCommand mongoCommand = new MongoCommand(params);
            mongoCommand.execute(db);
        }
    */

    @Test
    public void explain() throws IOException {
        MongoCommand mongoCommand = query("{name : \"MongoDB\","
                                          + "type : \"database\","
                                          + "count : 1,"
                                          + "info : {x : 203, y : 102}" +
                                          "})")
                                        .namespace(DATABASE_NAME, COLLECTION_NAME);
        List<Map> explain = mongoUtil.explain(mongoCommand);
        Assert.assertTrue(explain.iterator().hasNext());
    }

/*
    @Test
    public void export() throws IOException {
        MongoCommand mongoCommand = new MongoCommand("db.UnitTest.insert({"
                                                     + "\"name\" : \"MongoDB\","
                                                     + "\"type\" : \"database\","
                                                     + "\"count\" : 1,"
                                                     + "\"info\" : {\"x\" : 203,"
                                                     + "\"y\" : 102}})");
        mongoCommand.execute(db).get(0).isEmpty();
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
        Assert.assertEquals(mongoCommand.execute(db).size(), 5);
        mongoCommand.setLimit(6000);
        Assert.assertEquals(mongoCommand.execute(db).size(), MongoCommand.DEFAULT_LIMIT);
        Assert.assertEquals(new MongoCommand("db.limits.find({ \"bobby\" : { $ne : \"george\" }})"
                                             + ".sort({\"count\" : -1}).limit(10)").execute(db).size(), 10);
    }
*/
}