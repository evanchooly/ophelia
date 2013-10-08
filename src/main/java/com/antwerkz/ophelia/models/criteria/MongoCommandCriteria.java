package com.antwerkz.ophelia.models.criteria;

import java.util.List;

import com.antwerkz.critter.TypeSafeFieldEnd;
import com.antwerkz.critter.criteria.BaseCriteria;
import com.antwerkz.ophelia.models.MongoCommand;
import com.mongodb.WriteConcern;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

public class MongoCommandCriteria extends BaseCriteria<MongoCommand> {
  private String prefix = "";

  public MongoCommandCriteria(Datastore ds) {
    super(ds, MongoCommand.class);
  }

  public TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, org.bson.types.ObjectId> _id() {
    return new TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, org.bson.types.ObjectId>(this, query,
        prefix + "_id");
  }

  public MongoCommandCriteria _id(org.bson.types.ObjectId value) {
    new TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, org.bson.types.ObjectId>(this, query, prefix + "_id")
        .equal(value);
    return this;
  }

  public TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String> bookmark() {
    return new TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String>(this, query, prefix + "bookmark");
  }

  public MongoCommandCriteria bookmark(java.lang.String value) {
    new TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String>(this, query, prefix + "bookmark")
        .equal(value);
    return this;
  }

  public TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String> database() {
    return new TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String>(this, query, prefix + "database");
  }

  public MongoCommandCriteria database(java.lang.String value) {
    new TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String>(this, query, prefix + "database")
        .equal(value);
    return this;
  }

  public TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.Integer> limit() {
    return new TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.Integer>(this, query, prefix + "limit");
  }

  public MongoCommandCriteria limit(java.lang.Integer value) {
    new TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.Integer>(this, query, prefix + "limit")
        .equal(value);
    return this;
  }

  public TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.util.Map<java.lang.String, java.lang.String>> params() {
    return new TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.util.Map<java.lang.String, java.lang.String>>(
        this, query, prefix + "params");
  }

  public MongoCommandCriteria params(java.util.Map<java.lang.String, java.lang.String> value) {
    new TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.util.Map<java.lang.String, java.lang.String>>(this,
        query, prefix + "params").equal(value);
    return this;
  }

  public TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String> queryString() {
    return new TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String>(this, query,
        prefix + "queryString");
  }

  public MongoCommandCriteria queryString(java.lang.String value) {
    new TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.String>(this, query, prefix + "queryString")
        .equal(value);
    return this;
  }

  public TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.Boolean> readOnly() {
    return new TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.Boolean>(this, query,
        prefix + "readOnly");
  }

  public MongoCommandCriteria readOnly(java.lang.Boolean value) {
    new TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.Boolean>(this, query, prefix + "readOnly")
        .equal(value);
    return this;
  }

  public TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.Boolean> showCount() {
    return new TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.Boolean>(this, query,
        prefix + "showCount");
  }

  public MongoCommandCriteria showCount(java.lang.Boolean value) {
    new TypeSafeFieldEnd<MongoCommandCriteria, MongoCommand, java.lang.Boolean>(this, query, prefix + "showCount")
        .equal(value);
    return this;
  }

  public MongoCommandUpdater getUpdater() {
    return new MongoCommandUpdater();
  }

  public class MongoCommandUpdater {
    UpdateOperations<MongoCommand> updateOperations;

    public MongoCommandUpdater() {
      updateOperations = ds.createUpdateOperations(MongoCommand.class);
    }

    public UpdateResults<MongoCommand> update() {
      return ds.update(query(), updateOperations, false);
    }

    public UpdateResults<MongoCommand> update(WriteConcern wc) {
      return ds.update(query(), updateOperations, false, wc);
    }

    public UpdateResults<MongoCommand> upsert() {
      return ds.update(query(), updateOperations, true);
    }

    public UpdateResults<MongoCommand> upsert(WriteConcern wc) {
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

    public MongoCommandUpdater addAllTo_id(List<org.bson.types.ObjectId> values, boolean addDups) {
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

    public MongoCommandUpdater removeAllFrom_id(List<org.bson.types.ObjectId> values) {
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

    public MongoCommandUpdater addAllToBookmark(List<java.lang.String> values, boolean addDups) {
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

    public MongoCommandUpdater removeAllFromBookmark(List<java.lang.String> values) {
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

    public MongoCommandUpdater addAllToDatabase(List<java.lang.String> values, boolean addDups) {
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

    public MongoCommandUpdater removeAllFromDatabase(List<java.lang.String> values) {
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

    public MongoCommandUpdater addAllToLimit(List<java.lang.Integer> values, boolean addDups) {
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

    public MongoCommandUpdater removeAllFromLimit(List<java.lang.Integer> values) {
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

    public MongoCommandUpdater addAllToParams(List<java.util.Map<java.lang.String, java.lang.String>> values,
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

    public MongoCommandUpdater removeAllFromParams(List<java.util.Map<java.lang.String, java.lang.String>> values) {
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

    public MongoCommandUpdater addAllToQueryString(List<java.lang.String> values, boolean addDups) {
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

    public MongoCommandUpdater removeAllFromQueryString(List<java.lang.String> values) {
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

    public MongoCommandUpdater addAllToReadOnly(List<java.lang.Boolean> values, boolean addDups) {
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

    public MongoCommandUpdater removeAllFromReadOnly(List<java.lang.Boolean> values) {
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

    public MongoCommandUpdater addAllToShowCount(List<java.lang.Boolean> values, boolean addDups) {
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

    public MongoCommandUpdater removeAllFromShowCount(List<java.lang.Boolean> values) {
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
