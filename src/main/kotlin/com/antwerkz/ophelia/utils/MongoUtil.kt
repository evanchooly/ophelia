package com.antwerkz.ophelia.utils

import com.antwerkz.ophelia.models.MongoCommand
import com.antwerkz.sofia.Ophelia
import com.fasterxml.jackson.databind.ObjectMapper
import com.mongodb.BasicDBObject
import com.mongodb.MongoClient
import com.mongodb.client.FindIterable
import com.mongodb.client.model.UpdateOptions
import org.bson.Document
import java.util.ArrayList
import java.util.Arrays

public class MongoUtil(private val client: MongoClient) {

    public fun explain(command: MongoCommand): List<Document> {
        val explain = client.getDB(command.database)
                .getCollection(command.collection)
                .find(BasicDBObject(command.getQueryDocument()), BasicDBObject(command.getProjectionsDocument().toLinkedMap()))
                .explain()
                as BasicDBObject
        val document = Document()
        explain.entrySet()
                .forEach { document.put(it.key : String, it.value) }
        return Arrays.asList<Document>(document)
    }

    public fun query(command: MongoCommand): List<Document> {
        val query = getCursor(command)
        query.sort(command.getSortDocument())
        query.limit(command.limit)
        return extract(query)
    }

    public fun insert(command: MongoCommand) {
        val collection = client.getDatabase(command.database).getCollection(command.collection)
        return collection.insertOne(command.getInsertDocument())
    }

    public fun update(command: MongoCommand): Long {
        val collection = client.getDatabase(command.database).getCollection(command.collection)
        val upsert = command.upsert
        return if (command.multiple) {
            collection.updateOne(command.getQueryDocument(), command.getUpdateDocument(), UpdateOptions().upsert(upsert))
                    .getModifiedCount()
        } else {
            collection.updateMany(command.getQueryDocument(), command.getUpdateDocument(), UpdateOptions().upsert(upsert))
                    .getModifiedCount()
        }
    }

    public fun remove(command: MongoCommand): Long {
        val collection = client.getDatabase(command.database).getCollection(command.collection)
        val remove = collection.deleteMany(command.getQueryDocument())
        return remove.getDeletedCount()
    }

    public fun count(command: MongoCommand): Int {
        val collection = client.getDatabase(command.database).getCollection(command.collection)
        return collection.find(command.getQueryDocument()).count()
    }

    private fun getCursor(command: MongoCommand): FindIterable<Document> {
        return client.getDatabase(command.database)
                .getCollection(command.collection)
                .find(command.getQueryDocument())
                .projection(command.getProjectionsDocument())
    }


    private fun extract(execute: FindIterable<Document>): List<Document> {
        val list = ArrayList<Document>()
        execute.forEach { document ->
            list.add(document)
        }
        if (list.isEmpty()) {
            val map = Document()
            map.put("message", Ophelia().noResults())
            list.add(map)
        }
        return list
    }
}

fun Document.pretty(): String {
    return ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
}