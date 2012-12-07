package models;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.code.morphia.annotations.Entity;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import dao.Finder;
import dao.MongoModel;
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

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("ConnectionInfo {");
    sb.append(" _id='").append(getId()).append('\'');
    sb.append(", database='").append(database).append('\'');
    sb.append(", host='").append(host).append('\'');
    sb.append(", port=").append(port);
    sb.append(", queryString='").append(queryString).append('\'');
    sb.append('}');
    return sb.toString();
  }

  public static Finder<ConnectionInfo> find() {
    return new Finder<ConnectionInfo>(ConnectionInfo.class);
  }
}