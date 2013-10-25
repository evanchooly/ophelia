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
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, org.bson.types.ObjectId>(this, query,
        prefix + "_id").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String> bookmark() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "bookmark");
  }

  public MongoCommandCriteria bookmark(java.lang.String value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String>(this, query,
        prefix + "bookmark").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String> collection() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "collection");
  }

  public MongoCommandCriteria collection(java.lang.String value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String>(this, query,
        prefix + "collection").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String> database() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "database");
  }

  public MongoCommandCriteria database(java.lang.String value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String>(this, query,
        prefix + "database").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String> expanded() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "expanded");
  }

  public MongoCommandCriteria expanded(java.lang.String value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String>(this, query,
        prefix + "expanded").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.Integer> limit() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "limit");
  }

  public MongoCommandCriteria limit(java.lang.Integer value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.Integer>(this, query,
        prefix + "limit").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String> method() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "method");
  }

  public MongoCommandCriteria method(java.lang.String value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String>(this, query,
        prefix + "method").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String> mode() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "mode");
  }

  public MongoCommandCriteria mode(java.lang.String value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String>(this, query,
        prefix + "mode").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.util.Map<java.lang.String, java.lang.String>> params() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "params");
  }

  public MongoCommandCriteria params(java.util.Map<java.lang.String, java.lang.String> value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.util.Map<java.lang.String, java.lang.String>>(
        this, query, prefix + "params").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String> raw() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "raw");
  }

  public MongoCommandCriteria raw(java.lang.String value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String>(this, query,
        prefix + "raw").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.Boolean> readOnly() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "readOnly");
  }

  public MongoCommandCriteria readOnly(java.lang.Boolean value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.Boolean>(this, query,
        prefix + "readOnly").equal(value);
    return this;
  }

  public com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.Boolean> showCount() {
    return new com.antwerkz.critter.TypeSafeFieldEnd<>(this, query, prefix + "showCount");
  }

  public MongoCommandCriteria showCount(java.lang.Boolean value) {
    new com.antwerkz.critter.TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.Boolean>(this, query,
        prefix + "showCount").equal(value);
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

    public MongoCommandUpdater expanded(java.lang.String value) {
      updateOperations.set("expanded", value);
      return this;
    }

    public MongoCommandUpdater unsetExpanded(java.lang.String value) {
      updateOperations.unset("expanded");
      return this;
    }

    public MongoCommandUpdater addExpanded(java.lang.String value) {
      updateOperations.add("expanded", value);
      return this;
    }

    public MongoCommandUpdater addExpanded(java.lang.String value, boolean addDups) {
      updateOperations.add("expanded", value, addDups);
      return this;
    }

    public MongoCommandUpdater addAllToExpanded(java.util.List<java.lang.String> values, boolean addDups) {
      updateOperations.addAll("expanded", values, addDups);
      return this;
    }

    public MongoCommandUpdater removeFirstExpanded() {
      updateOperations.removeFirst("expanded");
      return this;
    }

    public MongoCommandUpdater removeLastExpanded() {
      updateOperations.removeLast("expanded");
      return this;
    }

    public MongoCommandUpdater removeFromExpanded(java.lang.String value) {
      updateOperations.removeAll("expanded", value);
      return this;
    }

    public MongoCommandUpdater removeAllFromExpanded(java.util.List<java.lang.String> values) {
      updateOperations.removeAll("expanded", values);
      return this;
    }

    public MongoCommandUpdater decExpanded() {
      updateOperations.dec("expanded");
      return this;
    }

    public MongoCommandUpdater incExpanded() {
      updateOperations.inc("expanded");
      return this;
    }

    public MongoCommandUpdater incExpanded(Number value) {
      updateOperations.inc("expanded", value);
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

    public MongoCommandUpdater method(java.lang.String value) {
      updateOperations.set("method", value);
      return this;
    }

    public MongoCommandUpdater unsetMethod(java.lang.String value) {
      updateOperations.unset("method");
      return this;
    }

    public MongoCommandUpdater addMethod(java.lang.String value) {
      updateOperations.add("method", value);
      return this;
    }

    public MongoCommandUpdater addMethod(java.lang.String value, boolean addDups) {
      updateOperations.add("method", value, addDups);
      return this;
    }

    public MongoCommandUpdater addAllToMethod(java.util.List<java.lang.String> values, boolean addDups) {
      updateOperations.addAll("method", values, addDups);
      return this;
    }

    public MongoCommandUpdater removeFirstMethod() {
      updateOperations.removeFirst("method");
      return this;
    }

    public MongoCommandUpdater removeLastMethod() {
      updateOperations.removeLast("method");
      return this;
    }

    public MongoCommandUpdater removeFromMethod(java.lang.String value) {
      updateOperations.removeAll("method", value);
      return this;
    }

    public MongoCommandUpdater removeAllFromMethod(java.util.List<java.lang.String> values) {
      updateOperations.removeAll("method", values);
      return this;
    }

    public MongoCommandUpdater decMethod() {
      updateOperations.dec("method");
      return this;
    }

    public MongoCommandUpdater incMethod() {
      updateOperations.inc("method");
      return this;
    }

    public MongoCommandUpdater incMethod(Number value) {
      updateOperations.inc("method", value);
      return this;
    }

    public MongoCommandUpdater mode(java.lang.String value) {
      updateOperations.set("mode", value);
      return this;
    }

    public MongoCommandUpdater unsetMode(java.lang.String value) {
      updateOperations.unset("mode");
      return this;
    }

    public MongoCommandUpdater addMode(java.lang.String value) {
      updateOperations.add("mode", value);
      return this;
    }

    public MongoCommandUpdater addMode(java.lang.String value, boolean addDups) {
      updateOperations.add("mode", value, addDups);
      return this;
    }

    public MongoCommandUpdater addAllToMode(java.util.List<java.lang.String> values, boolean addDups) {
      updateOperations.addAll("mode", values, addDups);
      return this;
    }

    public MongoCommandUpdater removeFirstMode() {
      updateOperations.removeFirst("mode");
      return this;
    }

    public MongoCommandUpdater removeLastMode() {
      updateOperations.removeLast("mode");
      return this;
    }

    public MongoCommandUpdater removeFromMode(java.lang.String value) {
      updateOperations.removeAll("mode", value);
      return this;
    }

    public MongoCommandUpdater removeAllFromMode(java.util.List<java.lang.String> values) {
      updateOperations.removeAll("mode", values);
      return this;
    }

    public MongoCommandUpdater decMode() {
      updateOperations.dec("mode");
      return this;
    }

    public MongoCommandUpdater incMode() {
      updateOperations.inc("mode");
      return this;
    }

    public MongoCommandUpdater incMode(Number value) {
      updateOperations.inc("mode", value);
      return this;
    }

    public MongoCommandUpdater params(java.util.Map<java.lang.String, java.lang.String> value) {
      updateOperations.set("params", value);
      return this;
    }

    public MongoCommandUpdater unsetParams(java.util.Map<java.lang.String, java.lang.String> value) {
      updateOperations.unset("params");
      return this;
    }

    public MongoCommandUpdater addParams(java.util.Map<java.lang.String, java.lang.String> value) {
      updateOperations.add("params", value);
      return this;
    }

    public MongoCommandUpdater addParams(java.util.Map<java.lang.String, java.lang.String> value, boolean addDups) {
      updateOperations.add("params", value, addDups);
      return this;
    }

    public MongoCommandUpdater addAllToParams(java.util.List<java.util.Map<java.lang.String, java.lang.String>> values,
        boolean addDups) {
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

    public MongoCommandUpdater removeFromParams(java.util.Map<java.lang.String, java.lang.String> value) {
      updateOperations.removeAll("params", value);
      return this;
    }

    public MongoCommandUpdater removeAllFromParams(
        java.util.List<java.util.Map<java.lang.String, java.lang.String>> values) {
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

    public MongoCommandUpdater raw(java.lang.String value) {
      updateOperations.set("raw", value);
      return this;
    }

    public MongoCommandUpdater unsetRaw(java.lang.String value) {
      updateOperations.unset("raw");
      return this;
    }

    public MongoCommandUpdater addRaw(java.lang.String value) {
      updateOperations.add("raw", value);
      return this;
    }

    public MongoCommandUpdater addRaw(java.lang.String value, boolean addDups) {
      updateOperations.add("raw", value, addDups);
      return this;
    }

    public MongoCommandUpdater addAllToRaw(java.util.List<java.lang.String> values, boolean addDups) {
      updateOperations.addAll("raw", values, addDups);
      return this;
    }

    public MongoCommandUpdater removeFirstRaw() {
      updateOperations.removeFirst("raw");
      return this;
    }

    public MongoCommandUpdater removeLastRaw() {
      updateOperations.removeLast("raw");
      return this;
    }

    public MongoCommandUpdater removeFromRaw(java.lang.String value) {
      updateOperations.removeAll("raw", value);
      return this;
    }

    public MongoCommandUpdater removeAllFromRaw(java.util.List<java.lang.String> values) {
      updateOperations.removeAll("raw", values);
      return this;
    }

    public MongoCommandUpdater decRaw() {
      updateOperations.dec("raw");
      return this;
    }

    public MongoCommandUpdater incRaw() {
      updateOperations.inc("raw");
      return this;
    }

    public MongoCommandUpdater incRaw(Number value) {
      updateOperations.inc("raw", value);
      return this;
    }

    public MongoCommandUpdater readOnly(java.lang.Boolean value) {
      updateOperations.set("readOnly", value);
      return this;
    }

    public MongoCommandUpdater unsetReadOnly(java.lang.Boolean value) {
      updateOperations.unset("readOnly");
      return this;
    }

    public MongoCommandUpdater addReadOnly(java.lang.Boolean value) {
      updateOperations.add("readOnly", value);
      return this;
    }

    public MongoCommandUpdater addReadOnly(java.lang.Boolean value, boolean addDups) {
      updateOperations.add("readOnly", value, addDups);
      return this;
    }

    public MongoCommandUpdater addAllToReadOnly(java.util.List<java.lang.Boolean> values, boolean addDups) {
      updateOperations.addAll("readOnly", values, addDups);
      return this;
    }

    public MongoCommandUpdater removeFirstReadOnly() {
      updateOperations.removeFirst("readOnly");
      return this;
    }

    public MongoCommandUpdater removeLastReadOnly() {
      updateOperations.removeLast("readOnly");
      return this;
    }

    public MongoCommandUpdater removeFromReadOnly(java.lang.Boolean value) {
      updateOperations.removeAll("readOnly", value);
      return this;
    }

    public MongoCommandUpdater removeAllFromReadOnly(java.util.List<java.lang.Boolean> values) {
      updateOperations.removeAll("readOnly", values);
      return this;
    }

    public MongoCommandUpdater decReadOnly() {
      updateOperations.dec("readOnly");
      return this;
    }

    public MongoCommandUpdater incReadOnly() {
      updateOperations.inc("readOnly");
      return this;
    }

    public MongoCommandUpdater incReadOnly(Number value) {
      updateOperations.inc("readOnly", value);
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
  }
}
