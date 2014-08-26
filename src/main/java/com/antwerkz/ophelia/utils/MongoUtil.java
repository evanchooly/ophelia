package com.antwerkz.ophelia.utils;

import com.antwerkz.ophelia.models.MongoCommand;
import com.antwerkz.sofia.Ophelia;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MongoUtil {
    private MongoClient client;

    public MongoUtil(final MongoClient client) {

        this.client = client;
    }

    public List<Map> explain(MongoCommand command) {
        return Arrays.asList(getCursor(command).explain().toMap());
    }

    public List<Map> query(MongoCommand command) {
        DBCursor query = getCursor(command);
        query.sort(command.getSortDocument());
        query.limit(command.getLimit());
        return extract(query);
    }

    public List<Map> insert(final MongoCommand command) {
        DBCollection collection = client.getDB(command.getDatabase()).getCollection(command.getCollection());
        return wrap(collection.insert(command.getInsertDocument()).getN());
    }

    public List<Map> update(final MongoCommand command) {
        DBCollection collection = client.getDB(command.getDatabase()).getCollection(command.getCollection());
        WriteResult update = collection.update(command.getQueryDocument(),
                                               command.getUpdateDocument(),
                                               command.getUpsert(),
                                               command.getMultiple());
        return wrap(update.getN());
    }
    public List<Map> remove(final MongoCommand command) {
        DBCollection collection = client.getDB(command.getDatabase()).getCollection(command.getCollection());
        return wrap(collection.remove(command.getQueryDocument()).getN());
    }

    public List<Map> count(final MongoCommand command) {
        Map<String, Long> map = new HashMap<>();
        DBCollection collection = client.getDB(command.getDatabase()).getCollection(command.getCollection());
        map.put(Ophelia.count(), collection.count(command.getQueryDocument()));
        return Arrays.asList(map);
    }

    private DBCursor getCursor(final MongoCommand command) {
        DBCollection collection = client.getDB(command.getDatabase()).getCollection(command.getCollection());
        return collection.find(command.getQueryDocument(), command.getProjectionsDocument());
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

    private List<Map> wrap(long number) {
        Map<String, Long> count = new TreeMap<>();
        count.put(Ophelia.count(), number);
        return Arrays.<Map>asList(count);
    }
}
