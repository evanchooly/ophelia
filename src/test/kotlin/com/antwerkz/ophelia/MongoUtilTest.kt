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
package com.antwerkz.ophelia

import com.antwerkz.ophelia.models.MongoCommand
import com.antwerkz.ophelia.utils.MongoUtil
import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBCollection
import com.mongodb.DBObject
import com.mongodb.MongoClient
import com.mongodb.client.MongoDatabase
import org.bson.Document
import org.bson.types.ObjectId
import org.testng.Assert
import org.testng.annotations.Test

import java.io.IOException
import java.net.UnknownHostException
import java.util.ArrayList

import java.lang.String.format

Test
public class MongoUtilTest [throws(javaClass<UnknownHostException>())] () {

    private val db: MongoDatabase
    private val mongoUtil: MongoUtil

    init {
        val client = MongoClient()
        db = client.getDatabase(DATABASE_NAME)
        mongoUtil = MongoUtil(client)
    }

    Test
    throws(javaClass<IOException>())
    public fun insertDocument() {
        db.getCollection(COLLECTION_NAME).drop()

        mongoUtil.insert(MongoCommand.insert("{name : \"John Doe\", \"age\" : 23 }").namespace(DATABASE_NAME, COLLECTION_NAME))

        val query = MongoCommand.query("{}").namespace(DATABASE_NAME, COLLECTION_NAME)

        val iterator = mongoUtil.query(query).iterator()
        val map = iterator.next()
        Assert.assertEquals(map.get("name"), "John Doe")
        Assert.assertEquals(map.get("age"), 23)
    }

    Test
    throws(javaClass<IOException>())
    public fun fields() {
        generateData()
        val query = MongoCommand.query("").setProjections("{count:0}").namespace(DATABASE_NAME, COLLECTION_NAME)

        val results = mongoUtil.query(query)
        for (result in results) {
            Assert.assertNull(result.get("count"))
        }
    }

    Test
    throws(javaClass<IOException>())
    public fun queryWithASort() {
        generateData()
        val query = MongoCommand.query("").namespace(DATABASE_NAME, COLLECTION_NAME)

        Assert.assertEquals(mongoUtil.query(query).iterator().next().get("count"), 0)

        query.setSort("{count:-1}")
        Assert.assertEquals(mongoUtil.query(query).iterator().next().get("count"), 9)
    }

    Test
    throws(javaClass<IOException>())
    public fun queryWithALimit() {
        generateData()
        val query = MongoCommand.query("").setLimit(5).namespace(DATABASE_NAME, COLLECTION_NAME)

        Assert.assertEquals(mongoUtil.query(query).size(), 5)

    }

    Test
    public fun simpleUpdate() {
        val collection = db.getCollection(COLLECTION_NAME)
        collection.drop()

        var command = MongoCommand.insert("{ name : \"John Doe\", age: 30 }").namespace(DATABASE_NAME, COLLECTION_NAME)
        mongoUtil.insert(command)

        val query = "{ name : \"John Doe\" }"
        command = MongoCommand.update(query, "{ \$inc : { age : 1 } }").setMultiple(false).setUpsert(false).namespace(DATABASE_NAME,
                COLLECTION_NAME)

        mongoUtil.update(command)

        val result = mongoUtil.query(MongoCommand.query(query).namespace(DATABASE_NAME, COLLECTION_NAME))
        Assert.assertEquals(result.get(0).get("age"), 31)
    }

    Test
    public fun simpleRemove() {
        val collection = db.getCollection(COLLECTION_NAME)
        collection.drop()

        var command = MongoCommand.insert("{ name : \"John Doe\", age: 30 }").namespace(DATABASE_NAME, COLLECTION_NAME)
        mongoUtil.insert(command)

        val query = "{ name : \"John Doe\" }"
        command = MongoCommand.remove(query).setMultiple(false).setUpsert(false).namespace(DATABASE_NAME, COLLECTION_NAME)

        mongoUtil.remove(command)

        val result = mongoUtil.query(MongoCommand.query(query).namespace(DATABASE_NAME, COLLECTION_NAME))
        Assert.assertNotNull(result.get(0).get("message"), result.toString())
    }

    private fun generateData() {
        val collection = db.getCollection(COLLECTION_NAME)
        collection.drop()
        val list = ArrayList<Document>()
        for (x in 0..10 - 1) {
            val objects = Document()
            objects.put("_id", ObjectId())
            objects.put("bobby", "mcgee")
            objects.put("count", x)
            list.add(objects)
        }
        collection.insertMany(list)
    }

    Test
    public fun emptyFind() {
        db.getCollection(COLLECTION_NAME).drop()

        mongoUtil.insert(MongoCommand.insert("{name : \"John Doe\", \"age\" : 23 }").namespace(DATABASE_NAME, COLLECTION_NAME))

        val query = MongoCommand.query("").namespace(DATABASE_NAME, COLLECTION_NAME)
        val iterator = mongoUtil.query(query).iterator()

        val map = iterator.next()
        Assert.assertEquals(map.get("name"), "John Doe")
        Assert.assertEquals(map.get("age"), 23)
    }

    Test
    throws(javaClass<IOException>())
    public fun systemIndexes() {
        val query = MongoCommand.query("").namespace(DATABASE_NAME, "system.indexes")
        val iterator = mongoUtil.query(query).iterator()
        Assert.assertTrue(iterator.hasNext())
    }

    Test
    throws(javaClass<IOException>())
    public fun objectId() {
        db.getCollection(COLLECTION_NAME).drop()

        mongoUtil.insert(MongoCommand.insert("{name : \"John Doe\", \"age\" : 23 }").namespace(DATABASE_NAME, COLLECTION_NAME))

        val map = mongoUtil.query(MongoCommand.query("{}").namespace(DATABASE_NAME, COLLECTION_NAME)).iterator().next()

        val loaded = mongoUtil.query(MongoCommand.query(format("{ _id : ObjectId(\"%s\") }", map.get("_id").toString())).namespace(DATABASE_NAME, COLLECTION_NAME)).iterator().next()
        Assert.assertEquals(map, loaded)
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

    Test
    throws(javaClass<IOException>())
    public fun explain() {
        val mongoCommand = MongoCommand
                .query("{name : \"MongoDB\"," + "type : \"database\"," + "count : 1," + "info : {x : 203, y : 102}" + "})")
                .namespace(DATABASE_NAME, COLLECTION_NAME)
        val explain = mongoUtil.explain(mongoCommand)
        Assert.assertTrue(explain.iterator().hasNext())
    }

    companion object {
        public val DATABASE_NAME: String = "testing"
        public val COLLECTION_NAME: String = "UnitTest"
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