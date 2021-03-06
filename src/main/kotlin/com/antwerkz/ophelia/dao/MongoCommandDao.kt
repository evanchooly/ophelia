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
package com.antwerkz.ophelia.dao


import com.antwerkz.ophelia.models.MongoCommand
import com.antwerkz.ophelia.models.criteria.MongoCommandCriteria
import org.bson.types.ObjectId
import org.mongodb.morphia.Datastore

public class MongoCommandDao(ds: Datastore) : OpheliaDao<MongoCommand>(ds, javaClass<MongoCommand>()) {

    public fun byBookmarkAndDatabase(bookmark: String, database: String): MongoCommand {
        val criteria = MongoCommandCriteria(ds)
        /*
                criteria.and(
                    criteria.bookmark(bookmark),
                    criteria.database(database)
                );
*/
        return criteria.query().get()
    }

    public fun findAll(database: String): List<MongoCommand> {
        val criteria = MongoCommandCriteria(ds)
        criteria.database().equal(database)
        return criteria.query().asList()
    }

    public fun delete(id: ObjectId) {
        val criteria = MongoCommandCriteria(ds)
        criteria._id().equal(id)
        ds.delete<MongoCommand>(criteria.query())
    }
}
