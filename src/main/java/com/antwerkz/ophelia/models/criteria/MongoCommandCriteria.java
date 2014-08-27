package com.antwerkz.ophelia.models.criteria;

import com.antwerkz.ophelia.models.MongoCommand;

public class MongoCommandCriteria extends com.antwerkz.critter.criteria.BaseCriteria<MongoCommand> {
  private String prefix = "";

  public MongoCommandCriteria(org.mongodb.morphia.Datastore ds) {
    super(ds, MongoCommand.class);
  }


  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, org.bson.types.ObjectId> _id() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "_id");
  }

  public MongoCommandCriteria _id(org.bson.types.ObjectId value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, org.bson.types.ObjectId>(this, query, prefix + "_id").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String> bookmark() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "bookmark");
  }

  public MongoCommandCriteria bookmark(java.lang.String value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String>(this, query, prefix + "bookmark").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String> collection() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "collection");
  }

  public MongoCommandCriteria collection(java.lang.String value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String>(this, query, prefix + "collection").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String> database() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "database");
  }

  public MongoCommandCriteria database(java.lang.String value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String>(this, query, prefix + "database").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String> insert() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "insert");
  }

  public MongoCommandCriteria insert(java.lang.String value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String>(this, query, prefix + "insert").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, com.mongodb.DBObject> insertDocument() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "insertDocument");
  }

  public MongoCommandCriteria insertDocument(com.mongodb.DBObject value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, com.mongodb.DBObject>(this, query, prefix + "insertDocument").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.Integer> limit() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "limit");
  }

  public MongoCommandCriteria limit(java.lang.Integer value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.Integer>(this, query, prefix + "limit").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.Boolean> multiple() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "multiple");
  }

  public MongoCommandCriteria multiple(java.lang.Boolean value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.Boolean>(this, query, prefix + "multiple").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.util.Map<java.lang.String,java.lang.String>> params() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "params");
  }

  public MongoCommandCriteria params(java.util.Map<java.lang.String,java.lang.String> value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.util.Map<java.lang.String,java.lang.String>>(this, query, prefix + "params").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String> projections() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "projections");
  }

  public MongoCommandCriteria projections(java.lang.String value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String>(this, query, prefix + "projections").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, com.mongodb.DBObject> projectionsDocument() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "projectionsDocument");
  }

  public MongoCommandCriteria projectionsDocument(com.mongodb.DBObject value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, com.mongodb.DBObject>(this, query, prefix + "projectionsDocument").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, com.mongodb.DBObject> queryDocument() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "queryDocument");
  }

  public MongoCommandCriteria queryDocument(com.mongodb.DBObject value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, com.mongodb.DBObject>(this, query, prefix + "queryDocument").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String> queryString() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "queryString");
  }

  public MongoCommandCriteria queryString(java.lang.String value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String>(this, query, prefix + "queryString").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.Boolean> showCount() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "showCount");
  }

  public MongoCommandCriteria showCount(java.lang.Boolean value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.Boolean>(this, query, prefix + "showCount").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String> sort() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "sort");
  }

  public MongoCommandCriteria sort(java.lang.String value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String>(this, query, prefix + "sort").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, com.mongodb.DBObject> sortDocument() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "sortDocument");
  }

  public MongoCommandCriteria sortDocument(com.mongodb.DBObject value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, com.mongodb.DBObject>(this, query, prefix + "sortDocument").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String> update() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "update");
  }

  public MongoCommandCriteria update(java.lang.String value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String>(this, query, prefix + "update").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, com.mongodb.DBObject> updateDocument() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "updateDocument");
  }

  public MongoCommandCriteria updateDocument(com.mongodb.DBObject value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, com.mongodb.DBObject>(this, query, prefix + "updateDocument").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.Boolean> upsert() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "upsert");
  }

  public MongoCommandCriteria upsert(java.lang.Boolean value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.Boolean>(this, query, prefix + "upsert").equal(value);
    return this;
  }


  public MongoCommandUpdater getUpdater() {
    return new MongoCommandUpdater();
  }

  public class MongoCommandUpdater {
    org.mongodb.morphia.query.UpdateOperations<MongoCommand> updateOperations;

    public MongoCommandUpdater() {
      updateOperations = ds.createUpdateOperations(MongoCommand.class);
    }

    public org.mongodb.morphia.query.UpdateResults<MongoCommand> update() {
      return ds.update(query(), updateOperations, false);
    }

    public org.mongodb.morphia.query.UpdateResults<MongoCommand> update(com.mongodb.WriteConcern wc) {
      return ds.update(query(), updateOperations, false, wc);
    }

    public org.mongodb.morphia.query.UpdateResults<MongoCommand> upsert() {
      return ds.update(query(), updateOperations, true);
    }

    public org.mongodb.morphia.query.UpdateResults<MongoCommand> upsert(com.mongodb.WriteConcern wc) {
      return ds.update(query(), updateOperations, true, wc);
    }

    public MongoCommandUpdater _id(org.bson.types.ObjectId value) {
      updateOperations.set("_id", value);
      return this;
    }

    public MongoCommandUpdater unset_id(org.bson.types.ObjectId value) {
      updateOperations.unset("_id");
      return this;
    }

    public MongoCommandUpdater add_id(org.bson.types.ObjectId value) {
      updateOperations.add("_id", value);
      return this;
    }

    public MongoCommandUpdater add_id(org.bson.types.ObjectId value, boolean addDups) {
      updateOperations.add("_id", value, addDups);
      return this;
    }

    public MongoCommandUpdater addAllTo_id(java.util.List<org.bson.types.ObjectId> values, boolean addDups) {
      updateOperations.addAll("_id", values, addDups);
      return this;
    }
  
    public MongoCommandUpdater removeFirst_id() {
      updateOperations.removeFirst("_id");
      return this;
    }
  
    public MongoCommandUpdater removeLast_id() {
      updateOperations.removeLast("_id");
      return this;
    }
  
    public MongoCommandUpdater removeFrom_id(org.bson.types.ObjectId value) {
      updateOperations.removeAll("_id", value);
      return this;
    }

    public MongoCommandUpdater removeAllFrom_id(java.util.List<org.bson.types.ObjectId> values) {
      updateOperations.removeAll("_id", values);
      return this;
    }
 
    public MongoCommandUpdater dec_id() {
      updateOperations.dec("_id");
      return this;
    }

    public MongoCommandUpdater inc_id() {
      updateOperations.inc("_id");
      return this;
    }

    public MongoCommandUpdater inc_id(Number value) {
      updateOperations.inc("_id", value);
      return this;
    }
    public MongoCommandUpdater bookmark(java.lang.String value) {
      updateOperations.set("bookmark", value);
      return this;
    }

    public MongoCommandUpdater unsetBookmark(java.lang.String value) {
      updateOperations.unset("bookmark");
      return this;
    }

    public MongoCommandUpdater addBookmark(java.lang.String value) {
      updateOperations.add("bookmark", value);
      return this;
    }

    public MongoCommandUpdater addBookmark(java.lang.String value, boolean addDups) {
      updateOperations.add("bookmark", value, addDups);
      return this;
    }

    public MongoCommandUpdater addAllToBookmark(java.util.List<java.lang.String> values, boolean addDups) {
      updateOperations.addAll("bookmark", values, addDups);
      return this;
    }
  
    public MongoCommandUpdater removeFirstBookmark() {
      updateOperations.removeFirst("bookmark");
      return this;
    }
  
    public MongoCommandUpdater removeLastBookmark() {
      updateOperations.removeLast("bookmark");
      return this;
    }
  
    public MongoCommandUpdater removeFromBookmark(java.lang.String value) {
      updateOperations.removeAll("bookmark", value);
      return this;
    }

    public MongoCommandUpdater removeAllFromBookmark(java.util.List<java.lang.String> values) {
      updateOperations.removeAll("bookmark", values);
      return this;
    }
 
    public MongoCommandUpdater decBookmark() {
      updateOperations.dec("bookmark");
      return this;
    }

    public MongoCommandUpdater incBookmark() {
      updateOperations.inc("bookmark");
      return this;
    }

    public MongoCommandUpdater incBookmark(Number value) {
      updateOperations.inc("bookmark", value);
      return this;
    }
    public MongoCommandUpdater collection(java.lang.String value) {
      updateOperations.set("collection", value);
      return this;
    }

    public MongoCommandUpdater unsetCollection(java.lang.String value) {
      updateOperations.unset("collection");
      return this;
    }

    public MongoCommandUpdater addCollection(java.lang.String value) {
      updateOperations.add("collection", value);
      return this;
    }

    public MongoCommandUpdater addCollection(java.lang.String value, boolean addDups) {
      updateOperations.add("collection", value, addDups);
      return this;
    }

    public MongoCommandUpdater addAllToCollection(java.util.List<java.lang.String> values, boolean addDups) {
      updateOperations.addAll("collection", values, addDups);
      return this;
    }
  
    public MongoCommandUpdater removeFirstCollection() {
      updateOperations.removeFirst("collection");
      return this;
    }
  
    public MongoCommandUpdater removeLastCollection() {
      updateOperations.removeLast("collection");
      return this;
    }
  
    public MongoCommandUpdater removeFromCollection(java.lang.String value) {
      updateOperations.removeAll("collection", value);
      return this;
    }

    public MongoCommandUpdater removeAllFromCollection(java.util.List<java.lang.String> values) {
      updateOperations.removeAll("collection", values);
      return this;
    }
 
    public MongoCommandUpdater decCollection() {
      updateOperations.dec("collection");
      return this;
    }

    public MongoCommandUpdater incCollection() {
      updateOperations.inc("collection");
      return this;
    }

    public MongoCommandUpdater incCollection(Number value) {
      updateOperations.inc("collection", value);
      return this;
    }
    public MongoCommandUpdater database(java.lang.String value) {
      updateOperations.set("database", value);
      return this;
    }

    public MongoCommandUpdater unsetDatabase(java.lang.String value) {
      updateOperations.unset("database");
      return this;
    }

    public MongoCommandUpdater addDatabase(java.lang.String value) {
      updateOperations.add("database", value);
      return this;
    }

    public MongoCommandUpdater addDatabase(java.lang.String value, boolean addDups) {
      updateOperations.add("database", value, addDups);
      return this;
    }

    public MongoCommandUpdater addAllToDatabase(java.util.List<java.lang.String> values, boolean addDups) {
      updateOperations.addAll("database", values, addDups);
      return this;
    }
  
    public MongoCommandUpdater removeFirstDatabase() {
      updateOperations.removeFirst("database");
      return this;
    }
  
    public MongoCommandUpdater removeLastDatabase() {
      updateOperations.removeLast("database");
      return this;
    }
  
    public MongoCommandUpdater removeFromDatabase(java.lang.String value) {
      updateOperations.removeAll("database", value);
      return this;
    }

    public MongoCommandUpdater removeAllFromDatabase(java.util.List<java.lang.String> values) {
      updateOperations.removeAll("database", values);
      return this;
    }
 
    public MongoCommandUpdater decDatabase() {
      updateOperations.dec("database");
      return this;
    }

    public MongoCommandUpdater incDatabase() {
      updateOperations.inc("database");
      return this;
    }

    public MongoCommandUpdater incDatabase(Number value) {
      updateOperations.inc("database", value);
      return this;
    }
    public MongoCommandUpdater insert(java.lang.String value) {
      updateOperations.set("insert", value);
      return this;
    }

    public MongoCommandUpdater unsetInsert(java.lang.String value) {
      updateOperations.unset("insert");
      return this;
    }

    public MongoCommandUpdater addInsert(java.lang.String value) {
      updateOperations.add("insert", value);
      return this;
    }

    public MongoCommandUpdater addInsert(java.lang.String value, boolean addDups) {
      updateOperations.add("insert", value, addDups);
      return this;
    }

    public MongoCommandUpdater addAllToInsert(java.util.List<java.lang.String> values, boolean addDups) {
      updateOperations.addAll("insert", values, addDups);
      return this;
    }
  
    public MongoCommandUpdater removeFirstInsert() {
      updateOperations.removeFirst("insert");
      return this;
    }
  
    public MongoCommandUpdater removeLastInsert() {
      updateOperations.removeLast("insert");
      return this;
    }
  
    public MongoCommandUpdater removeFromInsert(java.lang.String value) {
      updateOperations.removeAll("insert", value);
      return this;
    }

    public MongoCommandUpdater removeAllFromInsert(java.util.List<java.lang.String> values) {
      updateOperations.removeAll("insert", values);
      return this;
    }
 
    public MongoCommandUpdater decInsert() {
      updateOperations.dec("insert");
      return this;
    }

    public MongoCommandUpdater incInsert() {
      updateOperations.inc("insert");
      return this;
    }

    public MongoCommandUpdater incInsert(Number value) {
      updateOperations.inc("insert", value);
      return this;
    }
    public MongoCommandUpdater insertDocument(com.mongodb.DBObject value) {
      updateOperations.set("insertDocument", value);
      return this;
    }

    public MongoCommandUpdater unsetInsertDocument(com.mongodb.DBObject value) {
      updateOperations.unset("insertDocument");
      return this;
    }

    public MongoCommandUpdater addInsertDocument(com.mongodb.DBObject value) {
      updateOperations.add("insertDocument", value);
      return this;
    }

    public MongoCommandUpdater addInsertDocument(com.mongodb.DBObject value, boolean addDups) {
      updateOperations.add("insertDocument", value, addDups);
      return this;
    }

    public MongoCommandUpdater addAllToInsertDocument(java.util.List<com.mongodb.DBObject> values, boolean addDups) {
      updateOperations.addAll("insertDocument", values, addDups);
      return this;
    }
  
    public MongoCommandUpdater removeFirstInsertDocument() {
      updateOperations.removeFirst("insertDocument");
      return this;
    }
  
    public MongoCommandUpdater removeLastInsertDocument() {
      updateOperations.removeLast("insertDocument");
      return this;
    }
  
    public MongoCommandUpdater removeFromInsertDocument(com.mongodb.DBObject value) {
      updateOperations.removeAll("insertDocument", value);
      return this;
    }

    public MongoCommandUpdater removeAllFromInsertDocument(java.util.List<com.mongodb.DBObject> values) {
      updateOperations.removeAll("insertDocument", values);
      return this;
    }
 
    public MongoCommandUpdater decInsertDocument() {
      updateOperations.dec("insertDocument");
      return this;
    }

    public MongoCommandUpdater incInsertDocument() {
      updateOperations.inc("insertDocument");
      return this;
    }

    public MongoCommandUpdater incInsertDocument(Number value) {
      updateOperations.inc("insertDocument", value);
      return this;
    }
    public MongoCommandUpdater limit(java.lang.Integer value) {
      updateOperations.set("limit", value);
      return this;
    }

    public MongoCommandUpdater unsetLimit(java.lang.Integer value) {
      updateOperations.unset("limit");
      return this;
    }

    public MongoCommandUpdater addLimit(java.lang.Integer value) {
      updateOperations.add("limit", value);
      return this;
    }

    public MongoCommandUpdater addLimit(java.lang.Integer value, boolean addDups) {
      updateOperations.add("limit", value, addDups);
      return this;
    }

    public MongoCommandUpdater addAllToLimit(java.util.List<java.lang.Integer> values, boolean addDups) {
      updateOperations.addAll("limit", values, addDups);
      return this;
    }
  
    public MongoCommandUpdater removeFirstLimit() {
      updateOperations.removeFirst("limit");
      return this;
    }
  
    public MongoCommandUpdater removeLastLimit() {
      updateOperations.removeLast("limit");
      return this;
    }
  
    public MongoCommandUpdater removeFromLimit(java.lang.Integer value) {
      updateOperations.removeAll("limit", value);
      return this;
    }

    public MongoCommandUpdater removeAllFromLimit(java.util.List<java.lang.Integer> values) {
      updateOperations.removeAll("limit", values);
      return this;
    }
 
    public MongoCommandUpdater decLimit() {
      updateOperations.dec("limit");
      return this;
    }

    public MongoCommandUpdater incLimit() {
      updateOperations.inc("limit");
      return this;
    }

    public MongoCommandUpdater incLimit(Number value) {
      updateOperations.inc("limit", value);
      return this;
    }
    public MongoCommandUpdater multiple(java.lang.Boolean value) {
      updateOperations.set("multiple", value);
      return this;
    }

    public MongoCommandUpdater unsetMultiple(java.lang.Boolean value) {
      updateOperations.unset("multiple");
      return this;
    }

    public MongoCommandUpdater addMultiple(java.lang.Boolean value) {
      updateOperations.add("multiple", value);
      return this;
    }

    public MongoCommandUpdater addMultiple(java.lang.Boolean value, boolean addDups) {
      updateOperations.add("multiple", value, addDups);
      return this;
    }

    public MongoCommandUpdater addAllToMultiple(java.util.List<java.lang.Boolean> values, boolean addDups) {
      updateOperations.addAll("multiple", values, addDups);
      return this;
    }
  
    public MongoCommandUpdater removeFirstMultiple() {
      updateOperations.removeFirst("multiple");
      return this;
    }
  
    public MongoCommandUpdater removeLastMultiple() {
      updateOperations.removeLast("multiple");
      return this;
    }
  
    public MongoCommandUpdater removeFromMultiple(java.lang.Boolean value) {
      updateOperations.removeAll("multiple", value);
      return this;
    }

    public MongoCommandUpdater removeAllFromMultiple(java.util.List<java.lang.Boolean> values) {
      updateOperations.removeAll("multiple", values);
      return this;
    }
 
    public MongoCommandUpdater decMultiple() {
      updateOperations.dec("multiple");
      return this;
    }

    public MongoCommandUpdater incMultiple() {
      updateOperations.inc("multiple");
      return this;
    }

    public MongoCommandUpdater incMultiple(Number value) {
      updateOperations.inc("multiple", value);
      return this;
    }
    public MongoCommandUpdater params(java.util.Map<java.lang.String,java.lang.String> value) {
      updateOperations.set("params", value);
      return this;
    }

    public MongoCommandUpdater unsetParams(java.util.Map<java.lang.String,java.lang.String> value) {
      updateOperations.unset("params");
      return this;
    }

    public MongoCommandUpdater addParams(java.util.Map<java.lang.String,java.lang.String> value) {
      updateOperations.add("params", value);
      return this;
    }

    public MongoCommandUpdater addParams(java.util.Map<java.lang.String,java.lang.String> value, boolean addDups) {
      updateOperations.add("params", value, addDups);
      return this;
    }

    public MongoCommandUpdater addAllToParams(java.util.List<java.util.Map<java.lang.String,java.lang.String>> values, boolean addDups) {
      updateOperations.addAll("params", values, addDups);
      return this;
    }
  
    public MongoCommandUpdater removeFirstParams() {
      updateOperations.removeFirst("params");
      return this;
    }
  
    public MongoCommandUpdater removeLastParams() {
      updateOperations.removeLast("params");
      return this;
    }
  
    public MongoCommandUpdater removeFromParams(java.util.Map<java.lang.String,java.lang.String> value) {
      updateOperations.removeAll("params", value);
      return this;
    }

    public MongoCommandUpdater removeAllFromParams(java.util.List<java.util.Map<java.lang.String,java.lang.String>> values) {
      updateOperations.removeAll("params", values);
      return this;
    }
 
    public MongoCommandUpdater decParams() {
      updateOperations.dec("params");
      return this;
    }

    public MongoCommandUpdater incParams() {
      updateOperations.inc("params");
      return this;
    }

    public MongoCommandUpdater incParams(Number value) {
      updateOperations.inc("params", value);
      return this;
    }
    public MongoCommandUpdater projections(java.lang.String value) {
      updateOperations.set("projections", value);
      return this;
    }

    public MongoCommandUpdater unsetProjections(java.lang.String value) {
      updateOperations.unset("projections");
      return this;
    }

    public MongoCommandUpdater addProjections(java.lang.String value) {
      updateOperations.add("projections", value);
      return this;
    }

    public MongoCommandUpdater addProjections(java.lang.String value, boolean addDups) {
      updateOperations.add("projections", value, addDups);
      return this;
    }

    public MongoCommandUpdater addAllToProjections(java.util.List<java.lang.String> values, boolean addDups) {
      updateOperations.addAll("projections", values, addDups);
      return this;
    }
  
    public MongoCommandUpdater removeFirstProjections() {
      updateOperations.removeFirst("projections");
      return this;
    }
  
    public MongoCommandUpdater removeLastProjections() {
      updateOperations.removeLast("projections");
      return this;
    }
  
    public MongoCommandUpdater removeFromProjections(java.lang.String value) {
      updateOperations.removeAll("projections", value);
      return this;
    }

    public MongoCommandUpdater removeAllFromProjections(java.util.List<java.lang.String> values) {
      updateOperations.removeAll("projections", values);
      return this;
    }
 
    public MongoCommandUpdater decProjections() {
      updateOperations.dec("projections");
      return this;
    }

    public MongoCommandUpdater incProjections() {
      updateOperations.inc("projections");
      return this;
    }

    public MongoCommandUpdater incProjections(Number value) {
      updateOperations.inc("projections", value);
      return this;
    }
    public MongoCommandUpdater projectionsDocument(com.mongodb.DBObject value) {
      updateOperations.set("projectionsDocument", value);
      return this;
    }

    public MongoCommandUpdater unsetProjectionsDocument(com.mongodb.DBObject value) {
      updateOperations.unset("projectionsDocument");
      return this;
    }

    public MongoCommandUpdater addProjectionsDocument(com.mongodb.DBObject value) {
      updateOperations.add("projectionsDocument", value);
      return this;
    }

    public MongoCommandUpdater addProjectionsDocument(com.mongodb.DBObject value, boolean addDups) {
      updateOperations.add("projectionsDocument", value, addDups);
      return this;
    }

    public MongoCommandUpdater addAllToProjectionsDocument(java.util.List<com.mongodb.DBObject> values, boolean addDups) {
      updateOperations.addAll("projectionsDocument", values, addDups);
      return this;
    }
  
    public MongoCommandUpdater removeFirstProjectionsDocument() {
      updateOperations.removeFirst("projectionsDocument");
      return this;
    }
  
    public MongoCommandUpdater removeLastProjectionsDocument() {
      updateOperations.removeLast("projectionsDocument");
      return this;
    }
  
    public MongoCommandUpdater removeFromProjectionsDocument(com.mongodb.DBObject value) {
      updateOperations.removeAll("projectionsDocument", value);
      return this;
    }

    public MongoCommandUpdater removeAllFromProjectionsDocument(java.util.List<com.mongodb.DBObject> values) {
      updateOperations.removeAll("projectionsDocument", values);
      return this;
    }
 
    public MongoCommandUpdater decProjectionsDocument() {
      updateOperations.dec("projectionsDocument");
      return this;
    }

    public MongoCommandUpdater incProjectionsDocument() {
      updateOperations.inc("projectionsDocument");
      return this;
    }

    public MongoCommandUpdater incProjectionsDocument(Number value) {
      updateOperations.inc("projectionsDocument", value);
      return this;
    }
    public MongoCommandUpdater queryDocument(com.mongodb.DBObject value) {
      updateOperations.set("queryDocument", value);
      return this;
    }

    public MongoCommandUpdater unsetQueryDocument(com.mongodb.DBObject value) {
      updateOperations.unset("queryDocument");
      return this;
    }

    public MongoCommandUpdater addQueryDocument(com.mongodb.DBObject value) {
      updateOperations.add("queryDocument", value);
      return this;
    }

    public MongoCommandUpdater addQueryDocument(com.mongodb.DBObject value, boolean addDups) {
      updateOperations.add("queryDocument", value, addDups);
      return this;
    }

    public MongoCommandUpdater addAllToQueryDocument(java.util.List<com.mongodb.DBObject> values, boolean addDups) {
      updateOperations.addAll("queryDocument", values, addDups);
      return this;
    }
  
    public MongoCommandUpdater removeFirstQueryDocument() {
      updateOperations.removeFirst("queryDocument");
      return this;
    }
  
    public MongoCommandUpdater removeLastQueryDocument() {
      updateOperations.removeLast("queryDocument");
      return this;
    }
  
    public MongoCommandUpdater removeFromQueryDocument(com.mongodb.DBObject value) {
      updateOperations.removeAll("queryDocument", value);
      return this;
    }

    public MongoCommandUpdater removeAllFromQueryDocument(java.util.List<com.mongodb.DBObject> values) {
      updateOperations.removeAll("queryDocument", values);
      return this;
    }
 
    public MongoCommandUpdater decQueryDocument() {
      updateOperations.dec("queryDocument");
      return this;
    }

    public MongoCommandUpdater incQueryDocument() {
      updateOperations.inc("queryDocument");
      return this;
    }

    public MongoCommandUpdater incQueryDocument(Number value) {
      updateOperations.inc("queryDocument", value);
      return this;
    }
    public MongoCommandUpdater queryString(java.lang.String value) {
      updateOperations.set("queryString", value);
      return this;
    }

    public MongoCommandUpdater unsetQueryString(java.lang.String value) {
      updateOperations.unset("queryString");
      return this;
    }

    public MongoCommandUpdater addQueryString(java.lang.String value) {
      updateOperations.add("queryString", value);
      return this;
    }

    public MongoCommandUpdater addQueryString(java.lang.String value, boolean addDups) {
      updateOperations.add("queryString", value, addDups);
      return this;
    }

    public MongoCommandUpdater addAllToQueryString(java.util.List<java.lang.String> values, boolean addDups) {
      updateOperations.addAll("queryString", values, addDups);
      return this;
    }
  
    public MongoCommandUpdater removeFirstQueryString() {
      updateOperations.removeFirst("queryString");
      return this;
    }
  
    public MongoCommandUpdater removeLastQueryString() {
      updateOperations.removeLast("queryString");
      return this;
    }
  
    public MongoCommandUpdater removeFromQueryString(java.lang.String value) {
      updateOperations.removeAll("queryString", value);
      return this;
    }

    public MongoCommandUpdater removeAllFromQueryString(java.util.List<java.lang.String> values) {
      updateOperations.removeAll("queryString", values);
      return this;
    }
 
    public MongoCommandUpdater decQueryString() {
      updateOperations.dec("queryString");
      return this;
    }

    public MongoCommandUpdater incQueryString() {
      updateOperations.inc("queryString");
      return this;
    }

    public MongoCommandUpdater incQueryString(Number value) {
      updateOperations.inc("queryString", value);
      return this;
    }
    public MongoCommandUpdater showCount(java.lang.Boolean value) {
      updateOperations.set("showCount", value);
      return this;
    }

    public MongoCommandUpdater unsetShowCount(java.lang.Boolean value) {
      updateOperations.unset("showCount");
      return this;
    }

    public MongoCommandUpdater addShowCount(java.lang.Boolean value) {
      updateOperations.add("showCount", value);
      return this;
    }

    public MongoCommandUpdater addShowCount(java.lang.Boolean value, boolean addDups) {
      updateOperations.add("showCount", value, addDups);
      return this;
    }

    public MongoCommandUpdater addAllToShowCount(java.util.List<java.lang.Boolean> values, boolean addDups) {
      updateOperations.addAll("showCount", values, addDups);
      return this;
    }
  
    public MongoCommandUpdater removeFirstShowCount() {
      updateOperations.removeFirst("showCount");
      return this;
    }
  
    public MongoCommandUpdater removeLastShowCount() {
      updateOperations.removeLast("showCount");
      return this;
    }
  
    public MongoCommandUpdater removeFromShowCount(java.lang.Boolean value) {
      updateOperations.removeAll("showCount", value);
      return this;
    }

    public MongoCommandUpdater removeAllFromShowCount(java.util.List<java.lang.Boolean> values) {
      updateOperations.removeAll("showCount", values);
      return this;
    }
 
    public MongoCommandUpdater decShowCount() {
      updateOperations.dec("showCount");
      return this;
    }

    public MongoCommandUpdater incShowCount() {
      updateOperations.inc("showCount");
      return this;
    }

    public MongoCommandUpdater incShowCount(Number value) {
      updateOperations.inc("showCount", value);
      return this;
    }
    public MongoCommandUpdater sort(java.lang.String value) {
      updateOperations.set("sort", value);
      return this;
    }

    public MongoCommandUpdater unsetSort(java.lang.String value) {
      updateOperations.unset("sort");
      return this;
    }

    public MongoCommandUpdater addSort(java.lang.String value) {
      updateOperations.add("sort", value);
      return this;
    }

    public MongoCommandUpdater addSort(java.lang.String value, boolean addDups) {
      updateOperations.add("sort", value, addDups);
      return this;
    }

    public MongoCommandUpdater addAllToSort(java.util.List<java.lang.String> values, boolean addDups) {
      updateOperations.addAll("sort", values, addDups);
      return this;
    }
  
    public MongoCommandUpdater removeFirstSort() {
      updateOperations.removeFirst("sort");
      return this;
    }
  
    public MongoCommandUpdater removeLastSort() {
      updateOperations.removeLast("sort");
      return this;
    }
  
    public MongoCommandUpdater removeFromSort(java.lang.String value) {
      updateOperations.removeAll("sort", value);
      return this;
    }

    public MongoCommandUpdater removeAllFromSort(java.util.List<java.lang.String> values) {
      updateOperations.removeAll("sort", values);
      return this;
    }
 
    public MongoCommandUpdater decSort() {
      updateOperations.dec("sort");
      return this;
    }

    public MongoCommandUpdater incSort() {
      updateOperations.inc("sort");
      return this;
    }

    public MongoCommandUpdater incSort(Number value) {
      updateOperations.inc("sort", value);
      return this;
    }
    public MongoCommandUpdater sortDocument(com.mongodb.DBObject value) {
      updateOperations.set("sortDocument", value);
      return this;
    }

    public MongoCommandUpdater unsetSortDocument(com.mongodb.DBObject value) {
      updateOperations.unset("sortDocument");
      return this;
    }

    public MongoCommandUpdater addSortDocument(com.mongodb.DBObject value) {
      updateOperations.add("sortDocument", value);
      return this;
    }

    public MongoCommandUpdater addSortDocument(com.mongodb.DBObject value, boolean addDups) {
      updateOperations.add("sortDocument", value, addDups);
      return this;
    }

    public MongoCommandUpdater addAllToSortDocument(java.util.List<com.mongodb.DBObject> values, boolean addDups) {
      updateOperations.addAll("sortDocument", values, addDups);
      return this;
    }
  
    public MongoCommandUpdater removeFirstSortDocument() {
      updateOperations.removeFirst("sortDocument");
      return this;
    }
  
    public MongoCommandUpdater removeLastSortDocument() {
      updateOperations.removeLast("sortDocument");
      return this;
    }
  
    public MongoCommandUpdater removeFromSortDocument(com.mongodb.DBObject value) {
      updateOperations.removeAll("sortDocument", value);
      return this;
    }

    public MongoCommandUpdater removeAllFromSortDocument(java.util.List<com.mongodb.DBObject> values) {
      updateOperations.removeAll("sortDocument", values);
      return this;
    }
 
    public MongoCommandUpdater decSortDocument() {
      updateOperations.dec("sortDocument");
      return this;
    }

    public MongoCommandUpdater incSortDocument() {
      updateOperations.inc("sortDocument");
      return this;
    }

    public MongoCommandUpdater incSortDocument(Number value) {
      updateOperations.inc("sortDocument", value);
      return this;
    }
    public MongoCommandUpdater update(java.lang.String value) {
      updateOperations.set("update", value);
      return this;
    }

    public MongoCommandUpdater unsetUpdate(java.lang.String value) {
      updateOperations.unset("update");
      return this;
    }

    public MongoCommandUpdater addUpdate(java.lang.String value) {
      updateOperations.add("update", value);
      return this;
    }

    public MongoCommandUpdater addUpdate(java.lang.String value, boolean addDups) {
      updateOperations.add("update", value, addDups);
      return this;
    }

    public MongoCommandUpdater addAllToUpdate(java.util.List<java.lang.String> values, boolean addDups) {
      updateOperations.addAll("update", values, addDups);
      return this;
    }
  
    public MongoCommandUpdater removeFirstUpdate() {
      updateOperations.removeFirst("update");
      return this;
    }
  
    public MongoCommandUpdater removeLastUpdate() {
      updateOperations.removeLast("update");
      return this;
    }
  
    public MongoCommandUpdater removeFromUpdate(java.lang.String value) {
      updateOperations.removeAll("update", value);
      return this;
    }

    public MongoCommandUpdater removeAllFromUpdate(java.util.List<java.lang.String> values) {
      updateOperations.removeAll("update", values);
      return this;
    }
 
    public MongoCommandUpdater decUpdate() {
      updateOperations.dec("update");
      return this;
    }

    public MongoCommandUpdater incUpdate() {
      updateOperations.inc("update");
      return this;
    }

    public MongoCommandUpdater incUpdate(Number value) {
      updateOperations.inc("update", value);
      return this;
    }
    public MongoCommandUpdater updateDocument(com.mongodb.DBObject value) {
      updateOperations.set("updateDocument", value);
      return this;
    }

    public MongoCommandUpdater unsetUpdateDocument(com.mongodb.DBObject value) {
      updateOperations.unset("updateDocument");
      return this;
    }

    public MongoCommandUpdater addUpdateDocument(com.mongodb.DBObject value) {
      updateOperations.add("updateDocument", value);
      return this;
    }

    public MongoCommandUpdater addUpdateDocument(com.mongodb.DBObject value, boolean addDups) {
      updateOperations.add("updateDocument", value, addDups);
      return this;
    }

    public MongoCommandUpdater addAllToUpdateDocument(java.util.List<com.mongodb.DBObject> values, boolean addDups) {
      updateOperations.addAll("updateDocument", values, addDups);
      return this;
    }
  
    public MongoCommandUpdater removeFirstUpdateDocument() {
      updateOperations.removeFirst("updateDocument");
      return this;
    }
  
    public MongoCommandUpdater removeLastUpdateDocument() {
      updateOperations.removeLast("updateDocument");
      return this;
    }
  
    public MongoCommandUpdater removeFromUpdateDocument(com.mongodb.DBObject value) {
      updateOperations.removeAll("updateDocument", value);
      return this;
    }

    public MongoCommandUpdater removeAllFromUpdateDocument(java.util.List<com.mongodb.DBObject> values) {
      updateOperations.removeAll("updateDocument", values);
      return this;
    }
 
    public MongoCommandUpdater decUpdateDocument() {
      updateOperations.dec("updateDocument");
      return this;
    }

    public MongoCommandUpdater incUpdateDocument() {
      updateOperations.inc("updateDocument");
      return this;
    }

    public MongoCommandUpdater incUpdateDocument(Number value) {
      updateOperations.inc("updateDocument", value);
      return this;
    }
    public MongoCommandUpdater upsert(java.lang.Boolean value) {
      updateOperations.set("upsert", value);
      return this;
    }

    public MongoCommandUpdater unsetUpsert(java.lang.Boolean value) {
      updateOperations.unset("upsert");
      return this;
    }

    public MongoCommandUpdater addUpsert(java.lang.Boolean value) {
      updateOperations.add("upsert", value);
      return this;
    }

    public MongoCommandUpdater addUpsert(java.lang.Boolean value, boolean addDups) {
      updateOperations.add("upsert", value, addDups);
      return this;
    }

    public MongoCommandUpdater addAllToUpsert(java.util.List<java.lang.Boolean> values, boolean addDups) {
      updateOperations.addAll("upsert", values, addDups);
      return this;
    }
  
    public MongoCommandUpdater removeFirstUpsert() {
      updateOperations.removeFirst("upsert");
      return this;
    }
  
    public MongoCommandUpdater removeLastUpsert() {
      updateOperations.removeLast("upsert");
      return this;
    }
  
    public MongoCommandUpdater removeFromUpsert(java.lang.Boolean value) {
      updateOperations.removeAll("upsert", value);
      return this;
    }

    public MongoCommandUpdater removeAllFromUpsert(java.util.List<java.lang.Boolean> values) {
      updateOperations.removeAll("upsert", values);
      return this;
    }
 
    public MongoCommandUpdater decUpsert() {
      updateOperations.dec("upsert");
      return this;
    }

    public MongoCommandUpdater incUpsert() {
      updateOperations.inc("upsert");
      return this;
    }

    public MongoCommandUpdater incUpsert(Number value) {
      updateOperations.inc("upsert", value);
      return this;
    }
  }
}
