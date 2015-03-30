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

import com.antwerkz.ophelia.InvalidQueryException
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.base.Strings
import org.bson.BsonDocument
import org.bson.Document
import org.mongodb.morphia.annotations.Entity
import org.mongodb.morphia.annotations.Index
import org.mongodb.morphia.annotations.Indexes
import org.mongodb.morphia.annotations.Transient
import java.io.IOException
import java.lang.Boolean.FALSE
import java.util.TreeMap

Entity("queries")
Indexes(Index(name = "names", value = "database, bookmark", unique = true, dropDups = true))
public class MongoCommand : MongoModel<MongoCommand>() {

    public var bookmark: String = ""
        private set
    public var collection: String = "test"
    public var database: String = "test"
    public var insert: String = ""
    public var limit: Int = DEFAULT_LIMIT
    public var multiple: Boolean = FALSE
    //    public var params: java.util.Map<java.lang.String, java.lang.String> = HashMap<java.lang.String, java.lang.String>()
    public var projections: String = ""
    JsonProperty("query")
    public var queryString: String = ""
    public var showCount: Boolean = true
    public var sort: String = ""
    public var update: String = ""
    public var upsert: Boolean = FALSE

    JsonIgnore
    private Transient var insertDocument: Document = Document()
    JsonIgnore
    private Transient var projectionsDocument: Document = Document()
    JsonIgnore
    private Transient var queryDocument: Document = Document()
    JsonIgnore
    private Transient var sortDocument: Document = Document()
    JsonIgnore
    private Transient var updateDocument: Document = Document()

    public fun namespace(database: String, collection: String): MongoCommand {
        this.database = database
        this.collection = collection
        return this
    }

    public fun setLimit(limit: Int): MongoCommand {
        this.limit = if (limit < 1) DEFAULT_LIMIT else limit
        return this
    }

    public fun setMultiple(multiple: Boolean): MongoCommand {
        this.multiple = multiple
        return this
    }

    /*
        public fun setParams(params: Map<String, String>): MongoCommand {
            this.params = params
            return this
        }
    */

    public fun setProjections(projections: String): MongoCommand {
        this.projections = projections
        return this
    }

    public fun setShowCount(showCount: Boolean): MongoCommand {
        this.showCount = showCount
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

    public fun setUpsert(upsert: Boolean): MongoCommand {
        this.upsert = upsert
        return this
    }

    public fun getInsertDocument(): Document {
        if (insertDocument.isEmpty() && !insert.isEmpty()) {
            insertDocument = if (Strings.isNullOrEmpty(insert)) Document() else Document.parse(insert)
        }
        return insertDocument
    }

    public fun getProjectionsDocument(): Document {
        if (projectionsDocument.isEmpty() && !projections.isEmpty()) {
            projectionsDocument = parse(projections)
        }
        return projectionsDocument
    }

    public fun getQueryDocument(): Document {
        if (queryDocument.isEmpty() && !queryString.isEmpty()) {
            queryDocument = parse(queryString)
        }
        return queryDocument
    }

    public fun getSortDocument(): Document {
        if (sortDocument.isEmpty () && !sort.isEmpty()) {
            sortDocument = parse(sort)
        }

        return sortDocument
    }

    public fun getUpdateDocument(): Document {
        if (updateDocument.isEmpty() && !update.isEmpty()) {
            updateDocument = parse(update)
        }
        return updateDocument
    }

    public fun insert(insert: String): MongoCommand {
        this.insert = insert
        return this
    }

    public fun query(query: String): MongoCommand {
        queryString = query
        return this
    }

    public fun update(query: String, update: String): MongoCommand {
        query(query)
        this.update = update
        return this
    }

    public fun remove(remove: String): MongoCommand {
        queryString = remove
        return this
    }

    /*
        public fun expand(): String {
            for (entry in params.entrySet()) {
                queryString = queryString.replaceAll("\\{\\{" + entry.getKey() + "\\}\\}", entry.getValue())
            }
            return queryString.replace("\n", "")
        }
    */

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
    private fun parse(json: String?): Document {
        try {
            return Document(if (Strings.isNullOrEmpty(json)) BsonDocument() else BsonDocument.parse(json))
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

        if (bookmark != that.bookmark) {
            return false
        }
        if (collection != that.collection) {
            return false
        }
        if (database != that.database) {
            return false
        }
        if (insert != that.insert) {
            return false
        }
        if (limit != that.limit) {
            return false
        }
        if (multiple != that.multiple) {
            return false
        }
        /*
                if (params != that.params) {
                    return false
                }
        */
        if (projections != that.projections) {
            return false
        }
        if (queryString != that.queryString) {
            return false
        }
        if (showCount != that.showCount) {
            return false
        }
        if (sort != that.sort) {
            return false
        }
        if (update != that.update) {
            return false
        }
        if (upsert != that.upsert) {
            return false
        }

        return true
    }

    override fun hashCode(): Int {
        var result = bookmark.hashCode()
        result = 31 * result + collection.hashCode()
        result = 31 * result + database.hashCode()
        result = 31 * result + insert.hashCode()
        result = 31 * result + limit.hashCode()
        result = 31 * result + multiple.hashCode()
        //        result = 31 * result + params.hashCode()
        result = 31 * result + projections.hashCode()
        result = 31 * result + queryString.hashCode()
        result = 31 * result + showCount.hashCode()
        result = 31 * result + sort.hashCode()
        result = 31 * result + update.hashCode()
        result = 31 * result + upsert.hashCode()
        return result
    }

    override fun toString(): String {
        return "MongoCommand{upsert=$upsert, bookmark='$bookmark', collection='$collection', database='$database', insert='$insert', " +
                "limit=$limit, multiple=$multiple, projections='$projections', query='$queryString', showCount=$showCount, " +
                "sort='$sort', update='$update'}"
    }

    companion object {
        public val DEFAULT_LIMIT: Int = 100

    }
}