package com.antwerkz.ophelia.controllers;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.antwerkz.ophelia.models.Query;
import com.antwerkz.sofia.Ophelia;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;

public class Parser {
    private BasicDBObject queryExpression;
    private BasicDBObject keys;
    private String collection;
    private String method;
    private JacksonMapper mapper;
    private String queryString;
    private Query query;
    private Map<String, String> params;

    public Parser(Query query) throws IOException {
        this.query = query;
        this.params = query.getParams();
        this.queryString = scrub(query.getQueryString());
        for (Entry<String, String> entry : this.params.entrySet()) {
            queryString = queryString.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        if (this.queryString.endsWith(";")) {
            queryString = queryString.substring(0, queryString.length() - 1);
        }
        if (queryString.startsWith("db.")) {
            consume(3);
            parseQuery();
        } else {
            throw new InvalidQueryException(Ophelia.invalidQuery(query));
        }
    }

    public Long count(DB db) {
        return db.getCollection(getCollection()).count(getQueryExpression());
    }

    public List<Map> execute(DB db) {
        if (db != null) {
            DBCollection collection = db.getCollection(getCollection());
            if (query.getExplain()) {
                return explain(collection);
            } else {
                switch (method) {
                    case "drop":
                        doDrop(collection);
                        return null;
                    case "insert":
                        return insert(collection);
                    case "find":
                        return find(collection);
                    case "remove":
                        return remove(collection);
                    default:
                        throw new InvalidQueryException(Ophelia.unknownQueryMethod(method));
                }
            }
        }
        return null;
    }

    private void parseQuery() throws IOException {
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
    private Map<String, Object> parse() throws IOException {
        ObjectMapper mapper = getMapper();
        Map<String, Object> map = mapper.readValue(new ConsumingStringReader(queryString), LinkedHashMap.class);
        for (Entry<String, Object> o : map.entrySet()) {
            if (o.getValue() instanceof Map) {
                Map<String, Object> value = (Map<String, Object>) o.getValue();
                if (value.get("$oid") != null) {
                    o.setValue(new ObjectId((String) value.get("$oid")));
                }
            }
        }
        return map;
    }

    private String consume(int count) {
        return consume(count, true);
    }

    private String consume(int count, boolean trim) {
        String sub = queryString.substring(0, count);
        queryString = queryString.substring(count);
        if (trim) {
            queryString = queryString.trim();
        }
        return sub;
    }

    public ObjectMapper getMapper() {
        if (mapper == null) {
            mapper = new JacksonMapper();

        }
        return mapper;
    }

    private BasicDBObject getQueryExpression() {
        return queryExpression;
    }

    private String getCollection() {
        return collection;
    }

    private void doDrop(DBCollection collection) {
        collection.drop();
    }

    private List<Map> find(DBCollection collection) {
        return extract((DBCursor) collection.find(getQueryExpression(), keys).iterator());
    }

    private List<Map> extract(DBCursor execute) {
        List<Map> list = new ArrayList<>();
        Iterator<DBObject> iterator = execute.iterator();
        while (list.size() < query.getLimit() && iterator.hasNext()) {
            list.add(iterator.next().toMap());
        }
        if (list.isEmpty()) {
            Map<String, String> map = new TreeMap<>();
            map.put("message", Ophelia.noResults());
            list.add(map);
        }
        return list;
    }

    private List<Map> explain(DBCollection collection) {
        DBObject explain = collection.find(getQueryExpression(), keys).explain();
        return Arrays.asList(explain.toMap());
    }

    private List<Map> insert(DBCollection collection) {
        WriteResult insert = collection.insert(getQueryExpression());
        String error = insert.getError();
        if (error != null) {
            throw new IllegalArgumentException(error);
        }
        return wrap(insert.getN());
    }

    private List<Map> remove(DBCollection collection) {
        WriteResult remove = collection.remove(getQueryExpression());
        String error = remove.getError();
        if (error != null) {
            throw new IllegalArgumentException(error);
        }
        return wrap(remove.getN());
    }

    private String scrub(String query) throws IOException {
        String scrubbed = scrubObjectIds(query);
        scrubbed = scrubObjectIds(scrubbed);
        return scrubbed;
    }

    private String scrubObjectIds(String query) throws IOException {
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

    private List<Map> wrap(int number) {
        Map<String, Number> count = new TreeMap<>();
        count.put("count", number);
        return Arrays.<Map>asList(count);
    }

    private String extractValue(String value, int index) throws IOException {
        int first = value.indexOf("\"", index) + 1;
        int last = value.indexOf("\"", first + 1);
        String id = value.substring(first, last);
        Map<String, String> oid = new TreeMap<>();
        oid.put("$oid", id);
        return new ObjectMapper().writeValueAsString(oid);
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
}
