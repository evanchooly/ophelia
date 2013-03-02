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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.antwerkz.ophelia.controllers.JacksonMapper;
import com.antwerkz.ophelia.controllers.Parser;
import com.antwerkz.ophelia.models.Query;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.DB;
import com.mongodb.Mongo;
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
        Parser parser = new Parser(new Query("db.UnitTest.insert({"
            + "\"name\" : \"MongoDB\","
            + "\"type\" : \"database\","
            + "\"count\" : 1,"
            + "\"info\" : {\"x\" : 203,"
            + "\"y\" : 102}})"));
        parser.execute(db);
        parser = new Parser(new Query("db.UnitTest.find( { type : \"database\" } )")
        );
        Iterator<Map> iterator = parser.execute(db).iterator();
        Assert.assertTrue(iterator.hasNext());
        Map map = iterator.next();
        Assert.assertEquals(map.get("type"), "database");
        Assert.assertEquals(((Map) map.get("info")).get("y"), 102);
        parser.getMapper().writer().writeValueAsString(map);
    }

    @Test
    public void parameters() throws IOException {
        Parser parser = new Parser(new Query("db.users.find({}, {thumbnail:0});")
        );
        parser.execute(db);
    }

    @Test
    public void emptyFind() throws IOException {
        Parser parser = new Parser(new Query("db.Collection.find()"));
        parser.execute(db);
    }

    @Test
    public void systemIndexes() throws IOException {
        Parser parser = new Parser(new Query("db.system.indexes.find()"));
        parser.execute(db);
    }

    @Test
    public void objectId() throws IOException {
        Parser parser = new Parser(new Query("db.Collection.find( { _id : ObjectId(\"4f54216718c69681f6f14e13\") })")
        );
        parser.execute(db);
    }

    public void parameterized() throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("id", "i'm an ID");
        params.put("name", "and I'm a name!");
        Query query = new Query("db.ConnectedAccounts.find( {\n"
            + "  _id : \"{{id}}\",\n"
            + "  prop : \"{{id}}\",\n"
            + "  name : \"{{name}}\"\n"
            + "} )");
        query.setParams(params);
        new Parser(query).execute(db);
    }

    @Test
    public void explain() throws IOException {
        Query query = new Query("db.UnitTest.insert({"
            + "\"name\" : \"MongoDB\","
            + "\"type\" : \"database\","
            + "\"count\" : 1,"
            + "\"info\" : {\"x\" : 203,"
            + "\"y\" : 102}})");
        Parser parser = new Parser(query);
        Assert.assertFalse(parser.explain(db).get(0).isEmpty());
    }

    @Test
    public void export() throws IOException {
        Query query = new Query("db.UnitTest.insert({"
            + "\"name\" : \"MongoDB\","
            + "\"type\" : \"database\","
            + "\"count\" : 1,"
            + "\"info\" : {\"x\" : 203,"
            + "\"y\" : 102}})");
        Parser parser = new Parser(query);
        parser.setLimit(null);
        Assert.assertFalse(parser.execute(db).get(0).isEmpty());
    }

    @Test
    public void serialize() throws IOException {
        JacksonMapper mapper = new JacksonMapper();
        String json = "{\"bookmark\":\"\",\"queryString\":\"db.ConnectedAccounts.find( {\\n\\n} )\",\"limit\":100,"
            + "\"showCount\":true,\"params\":{},\"database\":\"_test\"}";
        ObjectNode node = (ObjectNode) mapper.readTree(json);
        Query query = mapper.convertValue(node, Query.class);
        Assert.assertTrue(query.getShowCount());
    }
}
