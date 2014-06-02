package com.antwerkz.ophelia.dao;

import com.antwerkz.ophelia.models.MongoModel;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

public class OpheliaDao<T> {
  private Datastore ds;

  private Class<T> clazz;

  public OpheliaDao(Datastore ds, Class<T> clazz) {
    this.ds = ds;
    this.clazz = clazz;
  }

  public Datastore getDs() {
    return ds;
  }

  public T find(final ObjectId id) {
    return ds.createQuery(clazz).field("_id").equal(id).get();
  }

  public void save(MongoModel model) {
    ds.save(model);
  }

  public void delete(MongoModel model) {
    ds.delete(model);
  }
}
