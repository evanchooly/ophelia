package models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.code.morphia.annotations.Entity;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import controllers.Parser;
import dao.Finder;
import dao.MongoModel;
import org.bson.types.ObjectId;
import plugins.MongOphelia;

@Entity("connection_info")
public class ConnectionInfo extends MongoModel<ConnectionInfo> {
  private static final int DEFAULT_LIMIT = 1000;
  @JsonProperty
  private String database;
  @JsonProperty
  private String queryString;
  @JsonProperty
  private String host = "127.0.0.1";
  @JsonProperty
  private Integer port = 27017;
  @JsonProperty
  private Integer limit = DEFAULT_LIMIT;
  @JsonProperty
  private Boolean readOnly = false;
  @JsonProperty
  private Boolean showCount = true;
  private ObjectId queryId;

  public String getDatabase() {
    return database;
  }

  public void setDatabase(String database) {
    this.database = database;
    save();
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
    save();
  }

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
    save();
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public Integer getLimit() {
    return limit == null || limit < 1 ? DEFAULT_LIMIT : limit;
  }

  public Boolean getReadOnly() {
    return readOnly;
  }

  public void setReadOnly(Boolean readOnly) {
    this.readOnly = readOnly == null ? false : readOnly;
  }

  public Boolean getShowCount() {
    return showCount;
  }

  public void setShowCount(Boolean showCount) {
    this.showCount = showCount;
  }

  @SuppressWarnings("unchecked")
  public Iterator<BasicDBObject> parse(String query) throws IOException {
    return (Iterator<BasicDBObject>) new Parser(query).execute(MongOphelia.getDB());
  }

  public Map<String, Object> query(String query) throws IOException {
    Map<String, Object> content = new TreeMap<>();
    Parser parser = new Parser(query);
    Object execute = parser.execute(MongOphelia.getDB());
    List<Map> list = null;
    if (execute instanceof DBCursor) {
      list = new ArrayList<>();
      for (DBObject result : (DBCursor) execute) {
        list.add(result.toMap());
      }
    } else if (execute instanceof Number) {
      Map<String, Number> count = new TreeMap<>();
      count.put("count", (Number) execute);
      list = Arrays.<Map>asList(count);
    }
    content.put("results", list);
    return content;
  }

  public List<String> getDatabaseNames() {
    return MongOphelia.getDatabaseNames();
  }

  public String getQueryString() {
    return queryString;
  }

  public void setQueryString(String query) {
    this.queryString = query;
    save();
  }

  public Map<String, Object> loadCollections() {
    TreeMap<String, Object> map = new TreeMap<>();
    DB db = MongOphelia.getDB();
    if (db != null) {
      Set<String> collections = db.getCollectionNames();
      for (String collection : collections) {
        CommandResult stats = db.getCollection(collection).getStats();
        map.put(collection, stats.get("count"));
      }
    }
    return map;
  }

  public ObjectId getQueryId() {
    return queryId;
  }

  public void setQueryId(ObjectId queryId) {
    this.queryId = queryId;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("ConnectionInfo {");
    sb.append(" _id='").append(getId()).append('\'');
    sb.append(", database='").append(database).append('\'');
    sb.append(", host='").append(host).append('\'');
    sb.append(", port=").append(port);
    sb.append(", queryId=").append(queryId);
    sb.append(", queryString='").append(queryString).append('\'');
    sb.append('}');
    return sb.toString();
  }

  public static Finder<ConnectionInfo> find() {
    return new Finder<ConnectionInfo>(ConnectionInfo.class);
  }
}