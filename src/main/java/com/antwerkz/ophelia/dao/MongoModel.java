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

import com.antwerkz.ophelia.utils.MongOphelia;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Id;

public class MongoModel<T> {
  @Id
  private ObjectId _id;

  public ObjectId getId() {
    return _id;
  }

  public void setId(ObjectId id) {
    this._id = id;
  }

  public static <R> R mongo(Operation<R> operation) {
    Datastore ds = MongOphelia.get();
    return operation.execute(ds);
  }

  public void save() {
    mongo(new Operation<Void>() {
      @Override
      public Void execute(Datastore ds) {
        if (_id == null) {
          _id = new ObjectId();
        }
        ds.save(MongoModel.this);
        return null;
      }
    });
  }

  public void delete() {
    mongo(new Operation<Void>() {
      @Override
      public Void execute(Datastore ds) {
        ds.delete(MongoModel.this);
        return null;
      }
    });
  }

  public interface Operation<R> {
    R execute(Datastore ds);
  }
}