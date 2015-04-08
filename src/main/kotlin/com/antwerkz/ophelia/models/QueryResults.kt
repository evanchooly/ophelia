package com.antwerkz.ophelia.models

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.bson.Document
import javax.xml.bind.annotation.XmlRootElement

XmlRootElement
JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class QueryResults(var dbResults: List<Document> = listOf(),
                          var resultCount: Int = 0,
                          var error: String = "") {
}