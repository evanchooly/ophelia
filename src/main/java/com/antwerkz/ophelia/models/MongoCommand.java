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
package com.antwerkz.ophelia.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.antwerkz.ophelia.controllers.InvalidQueryException;
import com.antwerkz.ophelia.utils.Parser;
import com.antwerkz.sofia.Ophelia;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

@Entity("queries")
@Indexes(
    @Index(name = "names", value = "database, bookmark", unique = true, dropDups = true)
)
public class MongoCommand extends MongoModel<MongoCommand> {
  public static final int DEFAULT_LIMIT = 100;

  private String expanded;

  private String bookmark;

  private String database;

  private String raw;

  private Integer limit;

  private Boolean readOnly;

  private Boolean showCount;

  private Map<String, String> params;

  private String method;

  private String collection;

  private String mode;

  public MongoCommand() {
    readOnly = false;
    showCount = true;
    limit = DEFAULT_LIMIT;
  }

  public MongoCommand(String raw) {
    this(raw, new HashMap<String, String>());
  }

  public MongoCommand(String raw, final Map<String, String> params) {
    this();
    this.raw = raw;
    this.params = params;
    this.expanded = expand();
    extractMetaData();
  }

  private void extractMetaData() {
    String preface = expanded.substring(0, expanded.indexOf('('));
    method = preface.substring(preface.lastIndexOf('.') + 1);
    collection = preface.substring(preface.indexOf('.') + 1, preface.lastIndexOf('.'));
    mode = preface.substring(0, preface.indexOf('.'));
  }

  public List<Map> execute(DB db) {
    if (db != null) {
      DBCollection dbCollection = db.getCollection(collection);
      switch (method) {
        case "drop":
          dbCollection.drop();
          return null;
        case "insert":
          return insert(db, dbCollection);
        case "find":
          return find(db);
        case "remove":
          return remove(dbCollection);
        case "count":
          return count(dbCollection);
        default:
          throw new InvalidQueryException(Ophelia.unknownQueryMethod(method));
      }
    }
    return null;
  }

  private List<Map> insert(final DB db, DBCollection collection) {
    DBCursor dbObjects = reconstructQuery(db);
    Parser parser = new Parser(this);
    WriteResult insert = collection.insert(parser.getQueryExpression());
    String error = insert.getError();
    if (error != null) {
      throw new IllegalArgumentException(error);
    }
    return wrap(insert.getN());
  }

  private List<Map> wrap(int number) {
    Map<String, Number> count = new TreeMap<>();
    count.put("count", number);
    return Arrays.<Map>asList(count);
  }

  private List<Map> find(final DB db) {
    return extract((DBCursor) reconstructQuery(db).iterator());
  }

  private List<Map> remove(DBCollection collection) {
    Parser parser = new Parser(this);
    WriteResult remove = collection.remove(parser.getQueryExpression());
    String error = remove.getError();
    if (error != null) {
      throw new IllegalArgumentException(error);
    }
    return wrap(remove.getN());
  }

  @SuppressWarnings("unchecked")
  private List<Map> count(final DBCollection collection) {
    Map map = new HashMap();
    map.put(Ophelia.count(), collection.count(null));
    return Arrays.asList(map);
  }

  private Object extract(final DBObject eval, final String first, final String second) {
    return ((DBObject) eval.get(first)).get(second);
  }

  public List<Map> explain(DB db) {
    return Arrays.asList(reconstructQuery(db).explain().toMap());
  }

  private DBCursor reconstructQuery(final DB db) {
    DBObject eval = (DBObject) db.eval(expanded.replace(method, "find"));
    DBCursor query = db.getCollection(collection).find(
        (DBObject) extract(eval, "_query", "query"),
        (DBObject) eval.get("_fields"));
    query.sort((DBObject) extract(eval, "_query", "orderby"));
    query.batchSize(((Double) eval.get("_batchSize")).intValue());
    int limit = ((Double) eval.get("_limit")).intValue();
    if (limit == 0) {
      limit = getLimit();
    }
    query.limit(limit == 0 ? DEFAULT_LIMIT : Math.min(limit, DEFAULT_LIMIT));
    query.skip(((Double) eval.get("_skip")).intValue());
    return query;
  }

  private List<Map> extract(DBCursor execute) {
    List<Map> list = new ArrayList<>();
    try (DBCursor cursor = execute) {
      for (final DBObject dbObject : cursor) {
        list.add(dbObject.toMap());
      }
      if (list.isEmpty()) {
        Map<String, String> map = new TreeMap<>();
        map.put("message", Ophelia.noResults());
        list.add(map);
      }
    }
    return list;
  }

  @JsonProperty
  public String getBookmark() {
    return bookmark;
  }

  public void setBookmark(String bookmark) {
    this.bookmark = bookmark;
  }

  @JsonProperty
  public String getDatabase() {
    return database;
  }

  public void setDatabase(String database) {
    this.database = database;
  }

  @JsonProperty("queryString")
  public String getRaw() {
    return raw;
  }

  public void setRaw(final String raw) {
    this.raw = raw;
    this.expanded = expand();
    extractMetaData();
  }

  @JsonProperty
  public Integer getLimit() {
    return limit == null || limit < 1 ? DEFAULT_LIMIT : limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  @JsonProperty
  public Boolean getReadOnly() {
    return readOnly;
  }

  public void setReadOnly(Boolean readOnly) {
    this.readOnly = coerce(readOnly);
  }

  @JsonProperty
  public Boolean getShowCount() {
    return showCount;
  }

  public void setShowCount(Boolean showCount) {
    this.showCount = coerce(showCount);
  }

  private boolean coerce(final Boolean value) {
    return value == null ? false : value;
  }

  @JsonProperty
  public Map<String, String> getParams() {
    return params;
  }

  public String expand() {
    String expanded = raw;
    if (params != null) {
      for (Entry<String, String> entry : params.entrySet()) {
        expanded = expanded.replaceAll("\\{\\{" + entry.getKey() + "\\}\\}", entry.getValue());
      }
    }
    return expanded.replace("\n", "");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MongoCommand mongoCommand = (MongoCommand) o;
    if (bookmark != null ? !bookmark.equals(mongoCommand.bookmark) : mongoCommand.bookmark != null) {
      return false;
    }
    if (raw != null ? !raw.equals(mongoCommand.raw) : mongoCommand.raw != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = bookmark != null ? bookmark.hashCode() : 0;
    result = 31 * result + (raw != null ? raw.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("Query");
    sb.append("{bookmark='").append(bookmark).append('\'');
    sb.append(", database='").append(database).append('\'');
    sb.append(", limit=").append(limit);
    sb.append(", readOnly=").append(readOnly);
    sb.append(", showCount=").append(showCount);
    sb.append(", queryString='").append(raw).append('\'');
    sb.append(", params=").append(params);
    sb.append('}');
    return sb.toString();
  }

}