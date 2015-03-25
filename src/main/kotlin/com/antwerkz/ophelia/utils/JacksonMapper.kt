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
package com.antwerkz.ophelia.utils

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.module.SimpleModule
import com.mongodb.DBRef
import org.bson.types.ObjectId

import java.io.IOException

public class JacksonMapper : ObjectMapper() {
    init {
        configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        val module = SimpleModule("jackson", Version.unknownVersion())
        module.addSerializer(ObjectIdSerializer())
        module.addSerializer(DBRefSerializer())
        registerModule(module)
    }

    private class ObjectIdSerializer : JsonSerializer<ObjectId>() {
        override fun handledType(): Class<ObjectId> {
            return javaClass<ObjectId>()
        }

        throws(javaClass<IOException>())
        override fun serialize(id: ObjectId, generator: JsonGenerator, provider: SerializerProvider) {
            generator.writeString(id.toString())
        }
    }

    private class DBRefSerializer : JsonSerializer<DBRef>() {
        override fun handledType(): Class<DBRef> {
            return javaClass<DBRef>()
        }

        throws(javaClass<IOException>())
        override fun serialize(value: DBRef, generator: JsonGenerator, provider: SerializerProvider) {
            generator.writeString(value.toString())
        }
    }
}
