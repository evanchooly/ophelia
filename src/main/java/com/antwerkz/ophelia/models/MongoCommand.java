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

import com.antwerkz.ophelia.controllers.InvalidQueryException;
import com.antwerkz.ophelia.utils.JacksonMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Index;
import org.mongodb.morphia.annotations.Indexes;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

@Entity("queries")
@Indexes(
            @Index(name = "names", value = "database, bookmark", unique = true, dropDups = true)
)
public class MongoCommand extends MongoModel<MongoCommand> {
    public static final int DEFAULT_LIMIT = 100;

    @JsonProperty
    private String database;

    @JsonProperty
    private String collection;

    @JsonProperty
    private String query;

    private DBObject queryDocument;

    @JsonProperty
    private String update;

    private DBObject updateDocument;

    @JsonProperty
    private String insert;

    private DBObject insertDocument;

    @JsonProperty
    private String bookmark;

    @JsonProperty
    private Integer limit;

    @JsonProperty
    private Boolean multiple;

    @JsonProperty
    private Boolean showCount;

    @JsonProperty
    private Map<String, String> params = new HashMap<>();

    public MongoCommand() {
        showCount = true;
        limit = DEFAULT_LIMIT;
    }

    public static MongoCommand query(String query) {
        MongoCommand command = new MongoCommand();
        command.query = query;

        return command;
    }

    public static MongoCommand update(String query, String update) {
        MongoCommand command = query(query);
        command.update = update;

        return command;
    }

    public static MongoCommand insert(String insert) {
        MongoCommand command = new MongoCommand();
        command.insert = insert;

        return command;
    }

    public String getBookmark() {
        return bookmark;
    }

    public MongoCommand setBookmark(String bookmark) {
        this.bookmark = bookmark;
        return this;

    }

    public String getCollection() {
        return collection;
    }

    public String getDatabase() {
        return database;
    }

    public DBObject getInsertDocument() {
        if (insertDocument == null && insert != null) {
            insertDocument = parse(insert);
        }
        return insertDocument;
    }

    public Integer getLimit() {
        return limit == null || limit < 1 ? DEFAULT_LIMIT : limit;
    }

    public MongoCommand setLimit(Integer limit) {
        this.limit = limit;
        return this;

    }

    public Boolean getMultiple() {
        return multiple;
    }

    public MongoCommand setMultiple(final Boolean multiple) {
        this.multiple = multiple;
        return this;
    }

    public MongoCommand setNamespace(String database, String collection) {
        this.database = database;
        this.collection = collection;
        return this;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public MongoCommand setParams(final Map<String, String> params) {
        this.params = params;
        return this;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(final String query) {
        this.query = query;
    }

    public DBObject getQueryDocument() {
        if (queryDocument == null && query != null) {
            queryDocument = parse(query);
        }
        return queryDocument;
    }

    public Boolean getShowCount() {
        return showCount;
    }

    public MongoCommand setShowCount(Boolean showCount) {
        this.showCount = coerce(showCount);
        return this;

    }

    public String getUpdate() {
        return update;
    }

    public MongoCommand setUpdate(final String update) {
        this.update = update;
        return this;
    }

    public DBObject getUpdateDocument() {
        if (updateDocument == null && update != null) {
            updateDocument = parse(update);
        }
        return updateDocument;
    }

    private boolean coerce(final Boolean value) {
        return value == null ? false : value;
    }

    public String expand() {
        if (params != null) {
            for (Entry<String, String> entry : params.entrySet()) {
                query = query.replaceAll("\\{\\{" + entry.getKey() + "\\}\\}", entry.getValue());
            }
        }
        return query.replace("\n", "");
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

    @SuppressWarnings("unchecked")
    private DBObject parse(final String json) {
        try {
            if (!Strings.isNullOrEmpty(json)) {
                Map<String, Object> map = new JacksonMapper().readValue(new StringReader(scrub(json)), LinkedHashMap.class);
                for (Entry<String, Object> o : map.entrySet()) {
                    if (o.getValue() instanceof Map) {
                        Map<String, Object> value = (Map<String, Object>) o.getValue();
                        if (value.get("$oid") != null) {
                            o.setValue(new ObjectId((String) value.get("$oid")));
                        }
                    }
                }
                return new BasicDBObject(map);
            } else {
                return new BasicDBObject();
            }
        } catch (IOException e) {
            throw new InvalidQueryException(e.getMessage(), e);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final MongoCommand that = (MongoCommand) o;

        if (bookmark != null ? !bookmark.equals(that.bookmark) : that.bookmark != null) {
            return false;
        }
        if (!collection.equals(that.collection)) {
            return false;
        }
        if (!database.equals(that.database)) {
            return false;
        }
        if (limit != null ? !limit.equals(that.limit) : that.limit != null) {
            return false;
        }
        if (multiple != null ? !multiple.equals(that.multiple) : that.multiple != null) {
            return false;
        }
        if (params != null ? !params.equals(that.params) : that.params != null) {
            return false;
        }
        if (!query.equals(that.query)) {
            return false;
        }
        if (showCount != null ? !showCount.equals(that.showCount) : that.showCount != null) {
            return false;
        }
        if (update != null ? !update.equals(that.update) : that.update != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = database.hashCode();
        result = 31 * result + collection.hashCode();
        result = 31 * result + query.hashCode();
        result = 31 * result + (update != null ? update.hashCode() : 0);
        result = 31 * result + (bookmark != null ? bookmark.hashCode() : 0);
        result = 31 * result + (limit != null ? limit.hashCode() : 0);
        result = 31 * result + (multiple != null ? multiple.hashCode() : 0);
        result = 31 * result + (showCount != null ? showCount.hashCode() : 0);
        result = 31 * result + (params != null ? params.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MongoCommand{" +
               "database='" + database + '\'' +
               ", collection='" + collection + '\'' +
               ", query='" + query + '\'' +
               ", update='" + update + '\'' +
               ", bookmark='" + bookmark + '\'' +
               ", limit=" + limit +
               ", multiple=" + multiple +
               ", showCount=" + showCount +
               ", params=" + params +
               '}';
    }

}