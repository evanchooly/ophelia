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
package com.antwerkz.ophelia.models

import com.antwerkz.ophelia.controllers.InvalidQueryException
import com.antwerkz.ophelia.utils.JacksonMapper
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.base.Strings
import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import org.bson.types.ObjectId
import org.mongodb.morphia.annotations.Entity
import org.mongodb.morphia.annotations.Index
import org.mongodb.morphia.annotations.Indexes

import java.io.IOException
import java.io.StringReader
import java.util.HashMap
import java.util.LinkedHashMap
import java.util.TreeMap

import java.lang.Boolean.FALSE

Entity("queries")
Indexes(@Index(name = "names", value = "database, bookmark", unique = true, dropDups = true) )
public class MongoCommand : MongoModel<MongoCommand>() {

    public var bookmark: String? = null
        private set
    public var collection: String? = null
    public var database: String? = null
    public var insert: String? = null
    private var limit: Int? = null
    public var multiple: Boolean? = FALSE
        private set
    public var params: Map<String, String>? = HashMap()
        private set
    public var projections: String? = null
        private set
    JsonProperty("query")
    public var queryString: String? = null
    public var showCount: Boolean? = FALSE
        private set
    public var sort: String? = null
        private set
    public var update: String? = null
        private set
    public var upsert: Boolean? = FALSE
        private set

    JsonIgnore
    private var insertDocument: DBObject? = null
    JsonIgnore
    private var projectionsDocument: DBObject? = null
    JsonIgnore
    private var queryDocument: DBObject? = null
    JsonIgnore
    private var sortDocument: DBObject? = null
    JsonIgnore
    private var updateDocument: DBObject? = null

    {
        showCount = true
        limit = DEFAULT_LIMIT
    }

    public fun namespace(database: String, collection: String): MongoCommand {
        this.database = database
        this.collection = collection
        return this
    }

    public fun setBookmark(bookmark: String): MongoCommand {
        this.bookmark = bookmark
        return this

    }

    public fun getLimit(): Int? {
        return if (limit == null || limit < 1) DEFAULT_LIMIT else limit
    }

    public fun setLimit(limit: Int?): MongoCommand {
        this.limit = limit
        return this
    }

    public fun setMultiple(multiple: Boolean?): MongoCommand {
        this.multiple = multiple
        return this
    }

    public fun setParams(params: Map<String, String>): MongoCommand {
        this.params = params
        return this
    }

    public fun setProjections(projections: String): MongoCommand {
        this.projections = projections
        return this
    }

    public fun setShowCount(showCount: Boolean?): MongoCommand {
        this.showCount = coerce(showCount)
        return this
    }

    public fun setSort(sort: String): MongoCommand {
        this.sort = sort
        return this
    }

    public fun setUpdate(update: String): MongoCommand {
        this.update = update
        return this
    }

    public fun setUpsert(upsert: Boolean?): MongoCommand {
        this.upsert = upsert
        return this
    }

    public fun getInsertDocument(): DBObject {
        if (insertDocument == null && insert != null) {
            insertDocument = parse(insert)
        }
        return insertDocument
    }

    public fun getProjectionsDocument(): DBObject {
        if (projectionsDocument == null && projections != null) {
            projectionsDocument = parse(projections)
        }
        return projectionsDocument
    }

    public fun getQueryDocument(): DBObject {
        if (queryDocument == null && queryString != null) {
            queryDocument = parse(queryString)
        }
        return queryDocument
    }

    public fun getSortDocument(): DBObject {
        if (sortDocument == null && sort != null) {
            sortDocument = parse(sort)
        }

        return sortDocument
    }

    public fun getUpdateDocument(): DBObject {
        if (updateDocument == null && update != null) {
            updateDocument = parse(update)
        }
        return updateDocument
    }

    private fun coerce(value: Boolean?): Boolean {
        return if (value == null) false else value
    }

    public fun expand(): String {
        if (params != null) {
            for (entry in params!!.entrySet()) {
                queryString = queryString!!.replaceAll("\\{\\{" + entry.getKey() + "\\}\\}", entry.getValue())
            }
        }
        return queryString!!.replace("\n", "")
    }

    private fun extractValue(value: String, index: Int): String {
        val first = value.indexOf("\"", index) + 1
        val last = value.indexOf("\"", first + 1)
        val id = value.substring(first, last)
        val oid = TreeMap<String, String>()
        oid.put("$oid", id)
        try {
            return ObjectMapper().writeValueAsString(oid)
        } catch (e: IOException) {
            throw RuntimeException(e.getMessage(), e)
        }

    }

    SuppressWarnings("unchecked")
    private fun parse(json: String): DBObject {
        try {
            if (!Strings.isNullOrEmpty(json)) {
                val map = JacksonMapper().readValue<LinkedHashMap<Any, Any>>(StringReader(scrub(json)), javaClass<LinkedHashMap<Any, Any>>())
                for (o in map.entrySet()) {
                    if (o.getValue() is Map<Any, Any>) {
                        val value = o.getValue() as Map<String, Any>
                        if (value.get("$oid") != null) {
                            o.setValue(ObjectId(value.get("$oid") as String))
                        }
                    }
                }
                return BasicDBObject(map)
            } else {
                return BasicDBObject()
            }
        } catch (e: IOException) {
            throw InvalidQueryException(e.getMessage(), e)
        }

    }

    private fun scrub(query: String): String {
        var scrubbed = scrubObjectIds(query)
        scrubbed = scrubObjectIds(scrubbed)
        return scrubbed
    }

    private fun scrubObjectIds(query: String): String {
        var query = query
        var index = -1
        while ((index = query.indexOf("ObjectId(\"", index + 1)) != -1) {
            val slug = query.substring(index - 4, index)
            if (slug == "new ") {
                index -= 4
            }
            query = String.format("%s%s%s", query.substring(0, index), extractValue(query, index), query.substring(query.indexOf(")", index) + 1))
            index = query.indexOf(")", index)
        }
        return query
    }

    override fun equals(o: Any?): Boolean {
        if (this == o) {
            return true
        }
        if (o == null || javaClass != o.javaClass) {
            return false
        }

        val that = o as MongoCommand

        if (if (bookmark != null) bookmark != that.bookmark else that.bookmark != null) {
            return false
        }
        if (if (collection != null) collection != that.collection else that.collection != null) {
            return false
        }
        if (if (database != null) database != that.database else that.database != null) {
            return false
        }
        if (if (insert != null) insert != that.insert else that.insert != null) {
            return false
        }
        if (if (limit != null) limit != that.limit else that.limit != null) {
            return false
        }
        if (if (multiple != null) multiple != that.multiple else that.multiple != null) {
            return false
        }
        if (if (params != null) params != that.params else that.params != null) {
            return false
        }
        if (if (projections != null) projections != that.projections else that.projections != null) {
            return false
        }
        if (if (queryString != null) queryString != that.queryString else that.queryString != null) {
            return false
        }
        if (if (showCount != null) showCount != that.showCount else that.showCount != null) {
            return false
        }
        if (if (sort != null) sort != that.sort else that.sort != null) {
            return false
        }
        if (if (update != null) update != that.update else that.update != null) {
            return false
        }
        if (if (upsert != null) upsert != that.upsert else that.upsert != null) {
            return false
        }

        return true
    }

    override fun hashCode(): Int {
        var result = if (bookmark != null) bookmark!!.hashCode() else 0
        result = 31 * result + (if (collection != null) collection!!.hashCode() else 0)
        result = 31 * result + (if (database != null) database!!.hashCode() else 0)
        result = 31 * result + (if (insert != null) insert!!.hashCode() else 0)
        result = 31 * result + (if (limit != null) limit!!.hashCode() else 0)
        result = 31 * result + (if (multiple != null) multiple!!.hashCode() else 0)
        result = 31 * result + (if (params != null) params!!.hashCode() else 0)
        result = 31 * result + (if (projections != null) projections!!.hashCode() else 0)
        result = 31 * result + (if (queryString != null) queryString!!.hashCode() else 0)
        result = 31 * result + (if (showCount != null) showCount!!.hashCode() else 0)
        result = 31 * result + (if (sort != null) sort!!.hashCode() else 0)
        result = 31 * result + (if (update != null) update!!.hashCode() else 0)
        result = 31 * result + (if (upsert != null) upsert!!.hashCode() else 0)
        return result
    }

    override fun toString(): String {
        return "MongoCommand{" + "upsert=" + upsert + ", bookmark='" + bookmark + '\'' + ", collection='" + collection + '\'' + ", database='" + database + '\'' + ", insert='" + insert + '\'' + ", limit=" + limit + ", multiple=" + multiple + ", params=" + params + ", projections='" + projections + '\'' + ", query='" + queryString + '\'' + ", showCount=" + showCount + ", sort='" + sort + '\'' + ", update='" + update + '\'' + '}'
    }

    class object {
        public val DEFAULT_LIMIT: Int = 100

        public fun query(query: String): MongoCommand {
            val command = MongoCommand()
            command.queryString = query

            return command
        }

        public fun update(query: String, update: String): MongoCommand {
            val command = query(query)
            command.update = update

            return command
        }

        public fun insert(insert: String): MongoCommand {
            val command = MongoCommand()
            command.insert = insert

            return command
        }

        public fun remove(remove: String): MongoCommand {
            val command = MongoCommand()
            command.queryString = remove

            return command
        }
    }
}