package models;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import controllers.Parser;
import play.db.jpa.Model;

@Entity
@Table(name = "connection_info")
public class ConnectionInfo extends Model {
  private String session;
  private String collection;
  private String database = "local";
  private String host = "127.0.0.1";
  private Integer port = 27017;
  private Boolean readOnly = Boolean.FALSE;
  private String query;

  public ConnectionInfo() {
  }

  public ConnectionInfo(String session) {
    this.session = session;
  }

  @Override
  public Object _key() {
    return getSession();
  }

  public String getCollection() {
    return collection;
  }

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

  public Boolean getReadOnly() {
    return readOnly;
  }

  public void setReadOnly(Boolean readOnly) {
    this.readOnly = readOnly;
    save();
  }

  public String getSession() {
    return session;
  }

  public void setSession(String session) {
    this.session = session;
    save();
  }

  public DB getDB() {
    DB db = getMongo().getDB(getDatabase());
    db.setReadOnly(getReadOnly());
    return db;
  }

  private Mongo getMongo() {
    try {
      return new Mongo(getHost(), getPort());
    } catch (UnknownHostException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  @SuppressWarnings("unchecked")
  public Iterator<BasicDBObject> parse(String query) throws IOException {
    return (Iterator<BasicDBObject>) new Parser(query).execute(getDB());
  }

  public Map<String, Object> query(String query) throws IOException {
    Map<String, Object> content = new TreeMap<>();
    Parser parser = new Parser(query);
    Object execute = parser.execute(getDB());
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
    Mongo mongo = getMongo();
    return mongo != null ? mongo.getDatabaseNames() : Collections.<String>emptyList();
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
    save();
  }

  public Map<String, Object> loadCollections() {
    TreeMap<String, Object> map = new TreeMap<>();
    DB db = getDB();
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
    sb.append(" session='").append(session).append('\'');
    sb.append(", collection='").append(collection).append('\'');
    sb.append(", database='").append(database).append('\'');
    sb.append(", host='").append(host).append('\'');
    sb.append(", port=").append(port);
    sb.append(", readOnly=").append(readOnly);
    sb.append(", query='").append(query).append('\'');
    sb.append('}');
    return sb.toString();
  }
}