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

import java.io.IOException
import java.io.OutputStream
import java.lang.reflect.Type
import javax.ws.rs.Produces
import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.MultivaluedMap
import javax.ws.rs.ext.MessageBodyWriter
import javax.ws.rs.ext.Provider

Provider
Produces(MediaType.APPLICATION_JSON)
public class JacksonBodyWriter : MessageBodyWriter<Any> {
    private val mapper = JacksonMapper()

    override
    public fun isWriteable(type: Class<*>, genericType: Type, annotations: Array<Annotation>, mediaType: MediaType): Boolean {
        return true
    }

    override
    public fun getSize(o: Any, type: Class<*>, genericType: Type, annotations: Array<Annotation>, mediaType: MediaType): Long {
        return (-1).toLong()
    }

    override
    throws(javaClass<IOException>(), javaClass<WebApplicationException>())
    public fun writeTo(o: Any, type: Class<*>, genericType: Type, annotations: Array<Annotation>, mediaType: MediaType,
                       httpHeaders: MultivaluedMap<String, Any>, entityStream: OutputStream) {
        mapper.writeValue(entityStream, o)
    }
}
