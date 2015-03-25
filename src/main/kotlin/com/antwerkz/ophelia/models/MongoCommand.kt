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
import org.bson.BsonDocument
import org.bson.BsonString
import org.bson.Document

Entity("queries")
Indexes(Index(name = "names", value = "database, bookmark", unique = true, dropDups = true) )
public class MongoCommand : MongoModel<MongoCommand>() {

    public var bookmark: String? = null
        private set
    public var collection: String = "test"
    public var database: String = "test"
    public var insert: String? = null
    private var limit: Int = DEFAULT_LIMIT
    public var multiple: Boolean? = FALSE
        private set
    public var params: Map<String, String> = HashMap()
        private set
    public var projections: String? = null
        private set
    JsonProperty("query")
    public var queryString: String = ""
    public var showCount: Boolean = FALSE
        private set
    public var sort: String? = null
        private set
    public var update: String? = null
        private set
    public var upsert: Boolean? = FALSE
        private set

    JsonIgnore
    private var insertDocument: Document? = null
    JsonIgnore
    private var projectionsDocument: BsonDocument? = null
    JsonIgnore
    private var queryDocument: BsonDocument? = null
    JsonIgnore
    private var sortDocument: BsonDocument? = null
    JsonIgnore
    private var updateDocument: BsonDocument? = null

    init {
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

    public fun getLimit(): Int {
        return if (limit < 1) DEFAULT_LIMIT else limit
    }

    public fun setLimit(limit: Int): MongoCommand {
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

    public fun getInsertDocument(): Document? {
        if (insertDocument == null) {
            insertDocument = if (Strings.isNullOrEmpty(insert)) Document() else Document.parse(insert)
        }
        return insertDocument
    }

    public fun getProjectionsDocument(): BsonDocument? {
        if (projectionsDocument == null && projections != null) {
            projectionsDocument = parse(projections)
        }
        return projectionsDocument
    }

    public fun getQueryDocument(): BsonDocument? {
        if (queryDocument == null) {
            queryDocument = parse(queryString)
        }
        return queryDocument
    }

    public fun getSortDocument(): BsonDocument? {
        if (sortDocument == null && sort != null) {
            sortDocument = parse(sort)
        }

        return sortDocument
    }

    public fun getUpdateDocument(): BsonDocument? {
        if (updateDocument == null && update != null) {
            updateDocument = parse(update)
        }
        return updateDocument
    }

    private fun coerce(value: Boolean?): Boolean {
        return value ?: false
    }

    public fun expand(): String {
        for (entry in params.entrySet()) {
            queryString = queryString.replaceAll("\\{\\{" + entry.getKey() + "\\}\\}", entry.getValue())
        }
        return queryString.replace("\n", "")
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
    private fun parse(json: String?): BsonDocument {
        try {
            return if (Strings.isNullOrEmpty(json)) BsonDocument() else BsonDocument.parse(json)
        } catch (e: IOException) {
            throw InvalidQueryException(e.getMessage()!!, e)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this == other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }

        val that = other as MongoCommand

        if (if (bookmark != null) bookmark != that.bookmark else that.bookmark != null) {
            return false
        }
        if (collection != that.collection) {
            return false
        }
        if (database != that.database) {
            return false
        }
        if (if (insert != null) insert != that.insert else that.insert != null) {
            return false
        }
        if (limit != that.limit) {
            return false
        }
        if (if (multiple != null) multiple != that.multiple else that.multiple != null) {
            return false
        }
        if (params != that.params) {
            return false
        }
        if (if (projections != null) projections != that.projections else that.projections != null) {
            return false
        }
        if (queryString != that.queryString) {
            return false
        }
        if (showCount != that.showCount) {
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
        result = 31 * result + (collection.hashCode())
        result = 31 * result + (database.hashCode())
        result = 31 * result + (if (insert != null) insert!!.hashCode() else 0)
        result = 31 * result + (limit.hashCode())
        result = 31 * result + (if (multiple != null) multiple!!.hashCode() else 0)
        result = 31 * result + (params.hashCode())
        result = 31 * result + (if (projections != null) projections!!.hashCode() else 0)
        result = 31 * result + (queryString.hashCode())
        result = 31 * result + (showCount.hashCode())
        result = 31 * result + (if (sort != null) sort!!.hashCode() else 0)
        result = 31 * result + (if (update != null) update!!.hashCode() else 0)
        result = 31 * result + (if (upsert != null) upsert!!.hashCode() else 0)
        return result
    }

    override fun toString(): String {
        return "MongoCommand{" + "upsert=" + upsert + ", bookmark='" + bookmark + '\'' + ", collection='" + collection + '\'' + ", database='" + database + '\'' + ", insert='" + insert + '\'' + ", limit=" + limit + ", multiple=" + multiple + ", params=" + params + ", projections='" + projections + '\'' + ", query='" + queryString + '\'' + ", showCount=" + showCount + ", sort='" + sort + '\'' + ", update='" + update + '\'' + '}'
    }

    companion object {
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