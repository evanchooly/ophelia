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
import java.util.TreeMap

public class ConnectionInfo(public val application: OpheliaApplication,
                            public var host: String = "127.0.0.1",
                            public var port: Int = 27017,
                            public var database: String = "test",
                            public var collection: String = "test") {


    public fun getDatabaseNames(): List<String> {
        return application.mongo.getDatabaseNames()
    }

    public fun loadCollections(): Map<String, Any> {
        val map = TreeMap<String, Any>()
        val db = application.mongo.getDB(database)
        if (db != null) {
            val collections = db.getCollectionNames()
            for (collection in collections) {
                map.put(collection, db.getCollection(collection).getStats().get("count"))
            }
        }
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