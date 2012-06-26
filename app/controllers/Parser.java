package controllers;

import java.io.IOException;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import utils.Localizer;

public class Parser {
  private BasicDBObject db;
  private String collection;
  private String method;
  private ObjectMapper mapper;

  public Parser(String query) throws IOException {
    if (query.endsWith(";")) {
      query = query.substring(0, query.length() - 1);
    }
    if (query.startsWith("db.")) {
      query = query.substring(3);
      parseQuery(query);
    } else {
      throw new InvalidQueryException(Localizer.invalidQuery(query));
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
      mapper = new ObjectMapper();
      mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
      SimpleModule module = new SimpleModule("jackson");
      module.addSerializer(new ObjectIdSerializer());
      mapper.registerModule(module);

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
    DBCursor cursor = collection.find(getDb());
    return cursor.iterator();
  }

  private Object doInsert(DBCollection collection) {
    WriteResult insert = collection.insert(getDb());
    String error = insert.getError();
    if (error != null) {
      throw new IllegalArgumentException(error);
    }
    return insert.getN();
  }

  private static class ObjectIdSerializer extends JsonSerializer<ObjectId> {
    @Override
    public Class<ObjectId> handledType() {
      return ObjectId.class;
    }

    @Override
    public void serialize(ObjectId id, JsonGenerator generator, SerializerProvider provider)
      throws IOException, JsonProcessingException {
      generator.writeString(id.toString());
    }
  }

}
