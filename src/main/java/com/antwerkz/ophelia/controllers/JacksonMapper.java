package com.antwerkz.ophelia.controllers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.bson.types.ObjectId;

import java.io.IOException;

public class JacksonMapper extends ObjectMapper {
    public JacksonMapper() {
        configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule module = new SimpleModule("jackson", Version.unknownVersion());
        module.addSerializer(new ObjectIdSerializer());
        module.addDeserializer(Boolean.class, new JsonDeserializer<Boolean>() {
            @Override
            public Boolean deserialize(JsonParser jp, DeserializationContext ctxt)
                    throws IOException, JsonProcessingException {
                return null;
            }
        });
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
