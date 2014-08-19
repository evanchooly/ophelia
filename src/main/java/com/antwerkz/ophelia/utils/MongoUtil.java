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

/*
    private DBCursor reconstructQuery(final DB db) {
        DBObject eval = (DBObject) db.eval(expanded.replace(method, "find"));
        DBCursor query = db.getCollection(collection).find(
                                                              (DBObject) extract(eval, "_query", "operation"),
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
*/

    public List<Map> query(MongoCommand command) {
        DBCursor query = getCursor(command);
//        query.sort((DBObject) extract(eval, "_query", "orderby"));
        query.limit(command.getLimit());
//        query.skip(((Double) eval.get("_skip")).intValue());
        return extract(query);
    }

    private DBCursor getCursor(final MongoCommand command) {
        return client.getDB(command.getDatabase()).getCollection(command.getCollection()).find(command.getQueryDocument());
    }

    public List<Map> insert(final MongoCommand command) {
        DBCollection collection = client.getDB(command.getDatabase()).getCollection(command.getCollection());
        return wrap(collection.insert(command.getInsertDocument()).getN());
    }

/*
    private List<Map> remove(DBCollection collection) {
        Parser parser = new Parser(this);
        WriteResult remove = collection.remove(parser.getQueryExpression());
        String error = remove.getError();
        if (error != null) {
            throw new IllegalArgumentException(error);
        }
        return wrap(remove.getN());
    }
*/

    @SuppressWarnings("unchecked")
    private List<Map> count(final DBCollection collection) {
        Map map = new HashMap();
        map.put(Ophelia.count(), collection.count(null));
        return Arrays.asList(map);
    }

    private Object extract(final DBObject eval, final String first, final String second) {
        return ((DBObject) eval.get(first)).get(second);
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

    private List<Map> wrap(int number) {
        Map<String, Number> count = new TreeMap<>();
        count.put("count", number);
        return Arrays.<Map>asList(count);
    }
}
