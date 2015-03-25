package com.antwerkz.ophelia.dao

import com.antwerkz.ophelia.models.MongoModel
import org.bson.types.ObjectId
import org.mongodb.morphia.Datastore

public open class OpheliaDao<T>(public val ds: Datastore, private val clazz: Class<T>) {

    public fun find(id: ObjectId): T {
        return ds.createQuery<T>(clazz).field("_id").equal(id).get()
    }

    public fun save(model: MongoModel<T>) {
        ds.save<MongoModel<T>>(model)
    }

    public fun delete(model: MongoModel<Any>) {
        ds.delete<MongoModel<Any>>(model)
    }
}
