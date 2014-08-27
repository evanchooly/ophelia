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
import com.fasterxml.jackson.annotation.JsonIgnore;
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

import static java.lang.Boolean.FALSE;

@Entity("queries")
@Indexes(
            @Index(name = "names", value = "database, bookmark", unique = true, dropDups = true)
)
public class MongoCommand extends MongoModel<MongoCommand> {
    public static final int DEFAULT_LIMIT = 100;

    private String bookmark;
    private String collection;
    private String database;
    private String insert;
    private Integer limit;
    private Boolean multiple = FALSE;
    private Map<String, String> params = new HashMap<>();
    private String projections;
    @JsonProperty("query")
    private String queryString;
    private Boolean showCount = FALSE;
    private String sort;
    private String update;
    private Boolean upsert = FALSE;

    @JsonIgnore
    private DBObject insertDocument;
    @JsonIgnore
    private DBObject projectionsDocument;
    @JsonIgnore
    private DBObject queryDocument;
    @JsonIgnore
    private DBObject sortDocument;
    @JsonIgnore
    private DBObject updateDocument;

    public MongoCommand() {
        showCount = true;
        limit = DEFAULT_LIMIT;
    }

    public static MongoCommand query(String query) {
        MongoCommand command = new MongoCommand();
        command.queryString = query;

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

    public static MongoCommand remove(String remove) {
        MongoCommand command = new MongoCommand();
        command.queryString = remove;

        return command;
    }

    public MongoCommand namespace(String database, String collection) {
        this.database = database;
        this.collection = collection;
        return this;
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

    public void setCollection(final String collection) {
        this.collection = collection;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(final String database) {
        this.database = database;
    }

    public String getInsert() {
        return insert;
    }

    public void setInsert(final String insert) {
        this.insert = insert;
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

    public Map<String, String> getParams() {
        return params;
    }

    public MongoCommand setParams(final Map<String, String> params) {
        this.params = params;
        return this;
    }

    public String getProjections() {
        return projections;
    }

    public MongoCommand setProjections(final String projections) {
        this.projections = projections;
        return this;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(final String queryString) {
        this.queryString = queryString;
    }

    public Boolean getShowCount() {
        return showCount;
    }

    public MongoCommand setShowCount(Boolean showCount) {
        this.showCount = coerce(showCount);
        return this;
    }

    public String getSort() {
        return sort;
    }

    public MongoCommand setSort(final String sort) {
        this.sort = sort;
        return this;
    }

    public String getUpdate() {
        return update;
    }

    public MongoCommand setUpdate(final String update) {
        this.update = update;
        return this;
    }

    public Boolean getUpsert() {
        return upsert;
    }

    public MongoCommand setUpsert(final Boolean upsert) {
        this.upsert = upsert;
        return this;
    }

    public DBObject getInsertDocument() {
        if (insertDocument == null && insert != null) {
            insertDocument = parse(insert);
        }
        return insertDocument;
    }

    public DBObject getProjectionsDocument() {
        if (projectionsDocument == null && projections != null) {
            projectionsDocument = parse(projections);
        }
        return projectionsDocument;
    }

    public DBObject getQueryDocument() {
        if (queryDocument == null && queryString != null) {
            queryDocument = parse(queryString);
        }
        return queryDocument;
    }

    public DBObject getSortDocument() {
        if (sortDocument == null && sort != null) {
            sortDocument = parse(sort);
        }

        return sortDocument;
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
                queryString = queryString.replaceAll("\\{\\{" + entry.getKey() + "\\}\\}", entry.getValue());
            }
        }
        return queryString.replace("\n", "");
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
        if (collection != null ? !collection.equals(that.collection) : that.collection != null) {
            return false;
        }
        if (database != null ? !database.equals(that.database) : that.database != null) {
            return false;
        }
        if (insert != null ? !insert.equals(that.insert) : that.insert != null) {
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
        if (projections != null ? !projections.equals(that.projections) : that.projections != null) {
            return false;
        }
        if (queryString != null ? !queryString.equals(that.queryString) : that.queryString != null) {
            return false;
        }
        if (showCount != null ? !showCount.equals(that.showCount) : that.showCount != null) {
            return false;
        }
        if (sort != null ? !sort.equals(that.sort) : that.sort != null) {
            return false;
        }
        if (update != null ? !update.equals(that.update) : that.update != null) {
            return false;
        }
        if (upsert != null ? !upsert.equals(that.upsert) : that.upsert != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = bookmark != null ? bookmark.hashCode() : 0;
        result = 31 * result + (collection != null ? collection.hashCode() : 0);
        result = 31 * result + (database != null ? database.hashCode() : 0);
        result = 31 * result + (insert != null ? insert.hashCode() : 0);
        result = 31 * result + (limit != null ? limit.hashCode() : 0);
        result = 31 * result + (multiple != null ? multiple.hashCode() : 0);
        result = 31 * result + (params != null ? params.hashCode() : 0);
        result = 31 * result + (projections != null ? projections.hashCode() : 0);
        result = 31 * result + (queryString != null ? queryString.hashCode() : 0);
        result = 31 * result + (showCount != null ? showCount.hashCode() : 0);
        result = 31 * result + (sort != null ? sort.hashCode() : 0);
        result = 31 * result + (update != null ? update.hashCode() : 0);
        result = 31 * result + (upsert != null ? upsert.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MongoCommand{" +
               "upsert=" + upsert +
               ", bookmark='" + bookmark + '\'' +
               ", collection='" + collection + '\'' +
               ", database='" + database + '\'' +
               ", insert='" + insert + '\'' +
               ", limit=" + limit +
               ", multiple=" + multiple +
               ", params=" + params +
               ", projections='" + projections + '\'' +
               ", query='" + queryString + '\'' +
               ", showCount=" + showCount +
               ", sort='" + sort + '\'' +
               ", update='" + update + '\'' +
               '}';
    }
}