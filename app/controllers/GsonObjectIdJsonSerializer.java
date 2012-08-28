package controllers;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.bson.types.ObjectId;

public class GsonObjectIdJsonSerializer implements JsonSerializer<ObjectId> {
  @Override
  public JsonElement serialize(ObjectId o, Type type, JsonSerializationContext context) {
    return new JsonPrimitive(o.toString());
  }
}
