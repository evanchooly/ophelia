package com.antwerkz.ophelia.dao;

import com.google.code.morphia.Datastore;
import org.bson.types.ObjectId;

public class Finder<T> {

    private Class<T> clazz;

    public Finder(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T byId(final ObjectId id) {
        return MongoModel.mongo(new MongoModel.Operation<T>() {
            @Override
            public T execute(Datastore ds) {
                return ds.createQuery(clazz).field("_id").equal(id).get();
            }
        });
    }
}
