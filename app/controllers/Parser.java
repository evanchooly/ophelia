package controllers;

import java.io.IOException;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.WriteResult;
import utils.Messages;

public class Parser {
  private BasicDBObject db;
  private String collection;
  private String method;
  private JacksonMapper mapper;

  public Parser(String query) throws IOException {
    if (query.endsWith(";")) {
      query = query.substring(0, query.length() - 1);
    }
    if (query.startsWith("db.")) {
      query = query.substring(3);
      parseQuery(query);
    } else {
      throw new InvalidQueryException(Messages.invalidQuery(query));
    }
  }

  private void parseQuery(String query) throws IOException {
    collection = query.substring(0, query.indexOf("."));
    query = query.substring(query.indexOf(".") + 1);
    method = query.substring(0, query.indexOf("("));
    query = query.substring(query.indexOf("(") + 1);
    query = query.substring(0, query.lastIndexOf("}") + 1);
    db = query.isEmpty() ? new BasicDBObject()
      : new BasicDBObject(getMapper().readValue(query, LinkedHashMap.class));
  }

  public ObjectMapper getMapper() {
    if (mapper == null) {
      mapper = new JacksonMapper();

    }
    return mapper;
  }

  public BasicDBObject getDb() {
    return db;
  }

  public String getCollection() {
    return collection;
  }

  public String getMethod() {
    return method;
  }

  public Object execute(DB db) {
    DBCollection collection = db.getCollection(getCollection());
    if (method.equals("insert")) {
      return doInsert(collection);
    }
    if (method.equals("find")) {
      return doFind(collection);
    }
    return null;
  }

  private Object doFind(DBCollection collection) {
    return collection.find(getDb()).iterator();
  }

  private Object doInsert(DBCollection collection) {
    WriteResult insert = collection.insert(getDb());
    String error = insert.getError();
    if (error != null) {
      throw new IllegalArgumentException(error);
    }
    return insert.getN();
  }
}
