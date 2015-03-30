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

import com.antwerkz.ophelia.InvalidQueryException
import com.antwerkz.ophelia.models.MongoCommand
import com.antwerkz.sofia.Ophelia
import com.fasterxml.jackson.databind.ObjectMapper
import com.mongodb.BasicDBObject
import com.mongodb.DB
import com.mongodb.DBCollection
import com.mongodb.DBCursor
import com.mongodb.client.MongoDatabase
import org.bson.Document
import org.bson.types.ObjectId

import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.io.StringReader
import java.util.LinkedHashMap

public class Parser(mongoCommand: MongoCommand) {
    public var queryExpression: Document = Document()

    private var keys: BasicDBObject? = null

    public var collection: String? = null

    private var method: String? = null

    private val mapper: JacksonMapper = JacksonMapper()

    private var queryString: String = ""

    public var limit: Int = MongoCommand.DEFAULT_LIMIT

    init {
        //    this.queryString = scrub(mongoCommand.expand());
        if (this.queryString.endsWith(";")) {
            queryString = queryString.substring(0, queryString.length() - 1)
        }
        if (queryString.startsWith("db.")) {
            consume(3)
            parseQuery()
        } else {
            throw InvalidQueryException(Ophelia().invalidQuery(mongoCommand))
        }
        limit = mongoCommand.limit
    }

    private fun consume(count: Int): String {
        return consume(count, true)
    }

    private fun parseQuery() {
        val preamble = queryString.substring(0, queryString.indexOf("("))
        collection = preamble.substring(0, preamble.lastIndexOf("."))
        method = preamble.substring(preamble.lastIndexOf(".") + 1)
        consume(preamble.length() + 1)
        if (queryString.startsWith("{")) {
            queryExpression = Document(parse())
        } else {
            queryExpression = Document()
        }
        if (queryString.startsWith(",")) {
            consume(1)
            keys = BasicDBObject(parse())
        }
        if (queryString.startsWith(")")) {
            consume(1)
        }
    }

    SuppressWarnings("unchecked")
    private fun parse(): Document {
        try {
            val reader = StringReader(queryString)
            val map: MutableMap<Any, Any> = mapper.readValue<LinkedHashMap<Any, Any>>(reader, javaClass<LinkedHashMap<Any, Any>>())
            for (entry: Map.Entry<Any, Any> in map.entrySet()) {
                if (entry.getValue() is Document) {
                    val value = entry.getValue() as Document
                    if (value.get("\$oid") != null) {
                        val objectId = ObjectId(value.get("\$oid") as String)
                        map.put(entry.key, objectId)
                    }
                }
            }
            return Document()
        } catch (e: IOException) {
            throw InvalidQueryException(e.getMessage()!!, e)
        }

    }

    private fun consume(count: Int, trim: Boolean): String {
        val sub = queryString.substring(0, count)
        queryString = queryString.substring(count)
        if (trim) {
            queryString = queryString.trim()
        }
        return sub
    }

    public fun count(db: MongoDatabase): Long {
        return db.getCollection(collection).count(queryExpression)
    }

/*
    public fun export(db: MongoDatabase): MongoInputStream {
        if ("find" != method) {
            throw IllegalArgumentException("Only find queries may be exported")
        }
        val collection = db.getCollection(collection)
        val dbObjects = collection.find(queryExpression, keys)
        return MongoInputStream(dbObjects)
    }
*/

/*
    private inner class ConsumingStringReader(query: String) : StringReader(query) {

        throws(javaClass<IOException>())
        override fun read(): Int {
            consume(1, false)
            return super.read()
        }

        throws(javaClass<IOException>())
        override fun read(cbuf: CharArray, off: Int, len: Int): Int {
            cbuf[off] = read().toChar()
            return 1
        }

        override fun close() {
        }
    }
*/

/*
    private class MongoInputStream(private val cursor: DBCursor) : InputStream() {

        private var bytes = ByteArrayInputStream("{".getBytes())

        private var commaSent = true

        private var closed = false

        throws(javaClass<IOException>())
        override fun available(): Int {
            return if (bytes.available() > 0) bytes.available() else (if (cursor.hasNext()) 1 else 0)
        }

        throws(javaClass<IOException>())
        override fun read(): Int {
            if (bytes.available() == 0) {
                if (!commaSent && cursor.hasNext()) {
                    bytes = ByteArrayInputStream(", ".getBytes())
                    commaSent = true
                } else if (cursor.hasNext()) {
                    commaSent = false
                    bytes = ByteArrayInputStream(JacksonMapper().writeValueAsBytes(cursor.next()))
                } else if (!closed) {
                    bytes = ByteArrayInputStream("}".getBytes())
                    closed = true
                } else {
                    return -1
                }
            }
            return bytes.read()
        }
    }
*/
}
