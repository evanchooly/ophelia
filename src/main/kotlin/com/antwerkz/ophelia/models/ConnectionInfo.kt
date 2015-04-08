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

import com.antwerkz.ophelia.OpheliaApplication
import com.antwerkz.ophelia.resources.QueryResource
import org.bson.Document
import java.util.ArrayList
import java.util.HashMap
import java.util.TreeMap

public class ConnectionInfo(application: OpheliaApplication,
                            public val host: String = "127.0.0.1",
                            public val port: Int = 27017,
                            public val database: String = "test",
                            public val collection: String = "test") {

    public val databases: List<String>

    public val collections: Map<String, Any>

    public val collectionInfo: Map<String, Any>

    init {
        databases = application.mongo.listDatabaseNames().toList();
        collections = loadCollections(application)
        collectionInfo = getCollectionInfo(application)
    }

    constructor(application: OpheliaApplication, info: ConnectionInfo,
                host: String = info.host,
                port: Int = info.port,
                database: String = info.database,
                collection: String = info.collection) : this(application, host, port, database, collection) {}

    public fun loadCollections(application: OpheliaApplication): Map<String, Any> {
        val map = TreeMap<String, Any>()
        val db = application.mongo.getDatabase(database)
        if (db != null) {
            val collections = db.listCollectionNames()
            for (collection in collections) {
                map.put(collection, getStats(application, collection).get("count"))
            }
        }
        return map
    }

    private fun getStats(application: OpheliaApplication, name: String): Document {
        val mongoDatabase = application.mongo.getDatabase(database)
        if (mongoDatabase.listCollectionNames().into(ArrayList<String>()).contains(name)) {
            return mongoDatabase.runCommand(Document("collstats", name))
        } else {
            return Document()
        }
    }

    private fun getCollectionInfo(application: OpheliaApplication): Map<String, Any> {
        val coll = application.mongo.getDatabase(database).getCollection(collection)
        val map = HashMap<String, Any>()
        val stats = getStats(application, collection)
        for (name in QueryResource.UNUSED_STATS) {
            stats.remove(name)
        }
        map.put("collectionStats", stats)
        map.put("indexes", coll.listIndexes())
        return map
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("ConnectionInfo")
        sb.append("{host='").append(host).append('\'')
        sb.append(", port=").append(port)
        sb.append('}')
        return sb.toString()
    }
}