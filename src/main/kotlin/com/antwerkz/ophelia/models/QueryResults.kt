package com.antwerkz.ophelia.models

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.bson.Document
import javax.xml.bind.annotation.XmlRootElement

XmlRootElement
JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class QueryResults() {
    public var info: ConnectionInfo? = null

    public var collections: Map<String, Any> = mapOf()

    public var databaseList: List<String> = listOf()

    public var dbResults: List<Document> = listOf()

    public var error: String = ""

    public var resultCount: Int = 0

    public var collectionStats: Document = Document()
}