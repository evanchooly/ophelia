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
package com.antwerkz.ophelia.models;

import com.antwerkz.ophelia.dao.Finder;
import com.antwerkz.ophelia.dao.MongoModel;
import org.mongodb.morphia.Datastore;

public class User extends MongoModel<User> {
  public String name;

  public Boolean admin;

  public User(String name, boolean admin) {
    this.name = name;
    this.admin = admin;
  }

  public static UserFinder find() {
    return new UserFinder();
  }

  public static class UserFinder extends Finder<User> {
    public UserFinder() {
      super(User.class);
    }

    public boolean initialized() {
      return mongo(new Operation<Boolean>() {
        @Override
        public Boolean execute(Datastore ds) {
          return ds.createQuery(User.class).get() != null;
        }
      });
    }
  }
}
