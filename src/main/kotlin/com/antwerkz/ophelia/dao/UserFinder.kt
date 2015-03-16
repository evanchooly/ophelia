package com.antwerkz.ophelia.dao

import com.antwerkz.ophelia.models.User
import org.mongodb.morphia.Datastore

public class UserFinder(ds: Datastore) : OpheliaDao<User>(ds, javaClass<User>()) {

    public fun initialized(): Boolean {
        return ds.createQuery<User>(javaClass<User>()).get() != null
    }
}
