package controllers;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.WriteResult;
import utils.Messages;

public class Parser {
  private BasicDBObject db;
  private BasicDBObject keys;
  private String collection;
  private String method;
  private JacksonMapper mapper;
  private String query;

  public Parser(String queryString) throws IOException {
    this.query = queryString;
    if (this.query.endsWith(";")) {
      query = query.substring(0, query.length() - 1);
    }
    if (query.startsWith("db.")) {
      consume(3);
      parseQuery();
    } else {
      throw new InvalidQueryException(Messages.invalidQuery(query));
    }
  }

  private void parseQuery() throws IOException {
    collection = consume(".");
    method = consume("(");
    if(query.startsWith("{")) {
      db = new BasicDBObject(getMapper().readValue(new ConsumingStringReader(query), LinkedHashMap.class));
    } else {
      db = new BasicDBObject();
    }
    if(query.startsWith(",")) {
      consume(1);
      keys = new BasicDBObject(getMapper().readValue(new ConsumingStringReader(query), LinkedHashMap.class));
    }
    if(query.startsWith(")")) {
      consume(1);
    }
  }

  private String consume(int count) {
    return consume(count, true);
  }

  private String consume(int count, boolean trim) {
    String sub = query.substring(0, count);
    query = query.substring(count);
    if(trim) {
      query = query.trim();
    }
    return sub;
  }

  private String consume(final String target) {
    //    query = consume(index + 1);
    String consume = consume(query.indexOf(target));
    consume(1);
    return consume;
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
    switch (method) {
      case "insert":
        return doInsert(collection);
      case "find":
        return doFind(collection);
      default:
        throw new InvalidQueryException(Messages.unknownQueryMethod(method));
    }
  }

  private Object doFind(DBCollection collection) {
    return collection.find(getDb(), keys).iterator();
  }

  private Object doInsert(DBCollection collection) {
    WriteResult insert = collection.insert(getDb());
    String error = insert.getError();
    if (error != null) {
      throw new IllegalArgumentException(error);
    }
    return insert.getN();
  }
  private class ConsumingStringReader extends StringReader {

    public ConsumingStringReader(final String query) {
      super(query);
    }

    @Override
    public int read() throws IOException {
      consume(1, false);
      return super.read();
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
      cbuf[off] = (char) read();
      return 1;
    }
    @Override
    public void close() {
    }

  }
}
