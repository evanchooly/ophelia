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
package com.antwerkz.ophelia.controllers

import com.antwerkz.ophelia.models.ConnectionInfo
import com.antwerkz.ophelia.models.MongoCommand
import com.fasterxml.jackson.databind.annotation.JsonSerialize

import javax.xml.bind.annotation.XmlRootElement

XmlRootElement
JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class QueryResults {
    public var collections: Map<String, Any>

    public var bookmarks: List<MongoCommand>

    public var databaseList: List<String>

    public var dbResults: List<Map<Any, Any>>

    public var error: String

    public var info: ConnectionInfo

    public var resultCount: Long? = null

    private var collectionStats: Map<String, String>? = null

    public fun getCollectionStats(): Map<Any, Any> {
        return collectionStats
    }

    public fun setCollectionStats(collectionStats: Map<Any, Any>) {
        this.collectionStats = collectionStats
    }
}
