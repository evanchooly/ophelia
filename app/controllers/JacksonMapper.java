package controllers;

import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;

import java.io.IOException;

public class JacksonMapper extends ObjectMapper {
    public JacksonMapper() {
        configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
//        configure(SerializationConfig.Feature.WRITE_NULL_PROPERTIES, false);;

        SimpleModule module = new SimpleModule("jackson", Version.unknownVersion());
        module.addSerializer(new ObjectIdSerializer());
        registerModule(module);
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
