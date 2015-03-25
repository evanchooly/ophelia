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

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.Type
import javax.ws.rs.Consumes
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.ext.MessageBodyReader
import javax.ws.rs.ext.Provider

import com.antwerkz.ophelia.models.MongoCommand
import com.fasterxml.jackson.databind.SerializationFeature

Provider
Consumes(MediaType.APPLICATION_JSON)
public class JacksonBodyReader : MessageBodyReader<MongoCommand> {
    private val mapper: JacksonMapper = JacksonMapper()

    init {
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
    }

    override
    public fun isReadable(type: Class<*>, genericType: Type, annotations: Array<Annotation>, mediaType: MediaType): Boolean {
        return javaClass<MongoCommand>() == type
    }

    override
    throws(javaClass<IOException>(), javaClass<WebApplicationException>())
    public fun readFrom(type: Class<MongoCommand>, genericType: Type, annotations: Array<Annotation>, mediaType: MediaType,
                        httpHeaders: MultivaluedMap<String, String>, entityStream: InputStream): MongoCommand {
        val reader = BufferedReader(InputStreamReader(entityStream))
        val content = reader.readLine()
        return mapper.readValue(content, javaClass<MongoCommand>())
    }
}