package com.antwerkz.ophelia.controllers;

import com.antwerkz.sofia.Ophelia;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Parser {
    private BasicDBObject db;
    private BasicDBObject keys;
    private String collection;
    private String method;
    private JacksonMapper mapper;
    private String query;

    public Parser(String queryString, Map<String, String> params) throws IOException {
        this.query = scrub(queryString);
        for (Entry<String, String> entry : params.entrySet()) {
            query = query.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }
        if (this.query.endsWith(";")) {
            query = query.substring(0, query.length() - 1);
        }
        if (query.startsWith("db.")) {
            consume(3);
            parseQuery(params);
        } else {
            throw new InvalidQueryException(Ophelia.invalidQuery(query));
        }
    }

    private void parseQuery(Map<String, String> params) throws IOException {
        String preamble = query.substring(0, query.indexOf("("));
        collection = preamble.substring(0, preamble.lastIndexOf("."));
        method = preamble.substring(preamble.lastIndexOf(".") + 1);
        consume(preamble.length() + 1);
        if (query.startsWith("{")) {
            db = new BasicDBObject(parse(params));
        } else {
            db = new BasicDBObject();
        }
        if (query.startsWith(",")) {
            consume(1);
            keys = new BasicDBObject(parse(params));
        }
        if (query.startsWith(")")) {
            consume(1);
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> parse(Map<String, String> params) throws IOException {
        ObjectMapper mapper = getMapper();
        Map<String, Object> map = mapper.readValue(new ConsumingStringReader(query), LinkedHashMap.class);
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
        String sub = query.substring(0, count);
        query = query.substring(count);
        if (trim) {
            query = query.trim();
        }
        return sub;
    }

    public ObjectMapper getMapper() {
        if (mapper == null) {
            mapper = new JacksonMapper();

        }
        return mapper;
    }

    public BasicDBObject getDb() {
        return db;
    }

    public String getCollection() {
        return collection;
    }

    public Object execute(DB db) {
        if (db != null) {
            DBCollection collection = db.getCollection(getCollection());
            switch (method) {
                case "drop":
                    doDrop(collection);
                    return null;
                case "insert":
                    return doInsert(collection);
                case "find":
                    return doFind(collection);
                case "remove":
                    return doRemove(collection);
                default:
                    throw new InvalidQueryException(Ophelia.unknownQueryMethod(method));
            }
        }
        return null;
    }

    private void doDrop(DBCollection collection) {
        collection.drop();
    }

    public Long count(DB db) {
        DBCollection collection = db.getCollection(getCollection());
        return collection.count(getDb());
    }

    private Object doFind(DBCollection collection) {
        return collection.find(getDb(), keys).iterator();
    }

    private Object doInsert(DBCollection collection) {
        WriteResult insert = collection.insert(getDb());
        String error = insert.getError();
        if (error != null) {
            throw new IllegalArgumentException(error);
        }
        return insert.getN();
    }

    private Object doRemove(DBCollection collection) {
        WriteResult remove = collection.remove(getDb());
        String error = remove.getError();
        if (error != null) {
            throw new IllegalArgumentException(error);
        }
        return remove.getN();
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
