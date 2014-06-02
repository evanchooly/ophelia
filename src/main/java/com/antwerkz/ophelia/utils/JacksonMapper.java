/**
 * Copyright (C) 2012-2014 Justin Lee <jlee@antwerkz.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.antwerkz.ophelia.utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.bson.types.ObjectId;

public class JacksonMapper extends ObjectMapper {
  public JacksonMapper() {
    configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
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
