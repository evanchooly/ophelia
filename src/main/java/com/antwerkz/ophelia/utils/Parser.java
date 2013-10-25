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
package com.antwerkz.ophelia.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.antwerkz.ophelia.controllers.InvalidQueryException;
import com.antwerkz.ophelia.models.MongoCommand;
import com.antwerkz.sofia.Ophelia;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import org.bson.types.ObjectId;

public class Parser {
  private BasicDBObject queryExpression;

  private BasicDBObject keys;

  private String collection;

  private String method;

  private JacksonMapper mapper;

  private String queryString;

  private Integer limit;

  public Parser(MongoCommand mongoCommand) {
    this.queryString = scrub(mongoCommand.expand());
    if (this.queryString.endsWith(";")) {
      queryString = queryString.substring(0, queryString.length() - 1);
    }
    if (queryString.startsWith("db.")) {
      consume(3);
      parseQuery();
    } else {
      throw new InvalidQueryException(Ophelia.invalidQuery(mongoCommand));
    }
    limit = mongoCommand.getLimit();
  }

  private String scrub(String query) {
    String scrubbed = scrubObjectIds(query);
    scrubbed = scrubObjectIds(scrubbed);
    return scrubbed;
  }

  private String scrubObjectIds(String query) {
    int index = -1;
    while ((index = query.indexOf("ObjectId(\"", index + 1)) != -1) {
      String slug = query.substring(index - 4, index);
      if (slug.equals("new ")) {
        index -= 4;
      }
      query = String.format("%s%s%s", query.substring(0, index),
          extractValue(query, index),
          query.substring(query.indexOf(")", index) + 1));
      index = query.indexOf(")", index);
    }
    return query;
  }

  private String extractValue(String value, int index) {
    int first = value.indexOf("\"", index) + 1;
    int last = value.indexOf("\"", first + 1);
    String id = value.substring(first, last);
    Map<String, String> oid = new TreeMap<>();
    oid.put("$oid", id);
    try {
      return new ObjectMapper().writeValueAsString(oid);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  private String consume(int count) {
    return consume(count, true);
  }

  private void parseQuery() {
    String preamble = queryString.substring(0, queryString.indexOf("("));
    collection = preamble.substring(0, preamble.lastIndexOf("."));
    method = preamble.substring(preamble.lastIndexOf(".") + 1);
    consume(preamble.length() + 1);
    if (queryString.startsWith("{")) {
      queryExpression = new BasicDBObject(parse());
    } else {
      queryExpression = new BasicDBObject();
    }
    if (queryString.startsWith(",")) {
      consume(1);
      keys = new BasicDBObject(parse());
    }
    if (queryString.startsWith(")")) {
      consume(1);
    }
  }

  @SuppressWarnings("unchecked")
  private Map<String, Object> parse() {
    try {
      Map<String, Object> map = getMapper().readValue(new ConsumingStringReader(queryString), LinkedHashMap.class);
      for (Entry<String, Object> o : map.entrySet()) {
        if (o.getValue() instanceof Map) {
          Map<String, Object> value = (Map<String, Object>) o.getValue();
          if (value.get("$oid") != null) {
            o.setValue(new ObjectId((String) value.get("$oid")));
          }
        }
      }
      return map;
    } catch (IOException e) {
      throw new InvalidQueryException(e.getMessage(), e);
    }
  }

  public String getCollection() {
    return collection;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(final Integer limit) {
    this.limit = limit;
  }

  public ObjectMapper getMapper() {
    if (mapper == null) {
      mapper = new JacksonMapper();
    }
    return mapper;
  }

  public BasicDBObject getQueryExpression() {
    return queryExpression;
  }

  private String consume(int count, boolean trim) {
    String sub = queryString.substring(0, count);
    queryString = queryString.substring(count);
    if (trim) {
      queryString = queryString.trim();
    }
    return sub;
  }

  public Long count(DB db) {
    return db.getCollection(getCollection()).count(getQueryExpression());
  }

  public MongoInputStream export(final DB db) {
    if (!"find".equals(method)) {
      throw new IllegalArgumentException("Only find queries may be exported");
    }
    DBCollection collection = db.getCollection(getCollection());
    DBCursor dbObjects = collection.find(getQueryExpression(), keys);
    return new MongoInputStream(dbObjects);
  }

  private class ConsumingStringReader extends StringReader {
    public ConsumingStringReader(final String query) {
      super(query);
    }

    @Override
    public int read() throws IOException {
      consume(1, false);
      return super.read();
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
      cbuf[off] = (char) read();
      return 1;
    }

    @Override
    public void close() {
    }
  }

  private static class MongoInputStream extends InputStream {
    private DBCursor cursor;

    private ByteArrayInputStream bytes = new ByteArrayInputStream("{".getBytes());

    private boolean commaSent = true;

    private boolean closed = false;

    public MongoInputStream(final DBCursor dbObjects) {
      cursor = dbObjects;
    }

    @Override
    public int available() throws IOException {
      return bytes.available() > 0 ? bytes.available() : (cursor.hasNext() ? 1 : 0);
    }

    @Override
    public int read() throws IOException {
      if (bytes.available() == 0) {
        if (!commaSent && cursor.hasNext()) {
          bytes = new ByteArrayInputStream(", ".getBytes());
          commaSent = true;
        } else if (cursor.hasNext()) {
          commaSent = false;
          bytes = new ByteArrayInputStream(new JacksonMapper().writeValueAsBytes(cursor.next()));
        } else if (!closed) {
          bytes = new ByteArrayInputStream("}".getBytes());
          closed = true;
        } else {
          return -1;
        }
      }
      return bytes.read();
    }
  }
}
