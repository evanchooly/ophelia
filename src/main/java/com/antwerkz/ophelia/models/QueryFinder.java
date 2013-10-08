/**
 * Copyright (C) 2012-2013 Justin Lee <jlee@antwerkz.com>
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
package com.antwerkz.ophelia.models;

import java.util.List;

import com.antwerkz.ophelia.dao.Finder;
import com.antwerkz.ophelia.dao.MongoModel;
import com.antwerkz.ophelia.dao.MongoModel.Operation;
import com.antwerkz.ophelia.models.criteria.MongoCommandCriteria;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

public class QueryFinder extends Finder<MongoCommand> {
  public QueryFinder() {
    super(MongoCommand.class);
  }

  public MongoCommand byBookmarkAndDatabase(final String bookmark, final String database) {
    return MongoModel.mongo(new Operation<MongoCommand>() {
      @Override
      public MongoCommand execute(Datastore ds) {
        MongoCommandCriteria criteria = new MongoCommandCriteria(ds);
//                criteria.and(
//                    criteria.bookmark().equal(bookmark),
//                    criteria.database().equal(database)
//                );
        return criteria.query().get();
      }
    });
  }

  public List<MongoCommand> findAll(final String database) {
    return MongoModel.mongo(new Operation<List<MongoCommand>>() {
      @Override
      public List<MongoCommand> execute(Datastore ds) {
        MongoCommandCriteria criteria = new MongoCommandCriteria(ds);
        criteria.database().equal(database);
        return criteria.query().asList();
      }
    });
  }

  public void delete(final ObjectId id) {
    MongoModel.mongo(new Operation<Void>() {
      @Override
      public Void execute(Datastore ds) {
        MongoCommandCriteria criteria = new MongoCommandCriteria(ds);
        criteria._id().equal(id);
        ds.delete(criteria.query());
        return null;
      }
    });
  }
}
