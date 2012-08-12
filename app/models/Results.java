package models;

import java.util.List;
import java.util.Map;

public class Results {
  private Map<String, Object> collections;
  private String database;
  private List<String> databaseList;
  private List<Map> dbResults;
  private String error;

  public String getDatabase() {
    return database;
  }

  public void setDatabase(String database) {
    this.database = database;
  }

  public Map<String, Object> getCollections() {
    return collections;
  }

  public void setCollections(Map<String,Object> collections) {
    this.collections = collections;
  }

  public List<String> getDatabaseList() {
    return databaseList;
  }

  public void setDatabaseList(List<String> databaseList) {
    this.databaseList = databaseList;
  }

  public List<Map> getDbResults() {
    return dbResults;
  }

  public void setDbResults(List<Map> dbResults) {
    this.dbResults = dbResults;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }
}
