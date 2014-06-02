package com.antwerkz.ophelia.dao;

import com.antwerkz.ophelia.models.User;
import org.mongodb.morphia.Datastore;

public class UserFinder extends OpheliaDao<User> {
  public UserFinder(Datastore ds) {
    super(ds, User.class);
  }

  public boolean initialized() {
    return getDs().createQuery(User.class).get() != null;
  }
}
