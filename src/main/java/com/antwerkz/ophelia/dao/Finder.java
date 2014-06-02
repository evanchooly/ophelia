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
package com.antwerkz.ophelia.dao;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

public class Finder<T> {
  private Class<T> clazz;

  public Finder(Class<T> clazz) {
    this.clazz = clazz;
  }

  public T byId(final ObjectId id) {
    return MongoModel.mongo(new MongoModel.Operation<T>() {
      @Override
      public T execute(Datastore ds) {
        return ds.createQuery(clazz).field("_id").equal(id).get();
      }
    });
  }
}
