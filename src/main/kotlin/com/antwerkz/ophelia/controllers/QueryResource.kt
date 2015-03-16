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
package com.antwerkz.ophelia.controllers

import com.antwerkz.ophelia.OpheliaApplication
import com.antwerkz.ophelia.dao.MongoCommandDao
import com.antwerkz.ophelia.models.ConnectionInfo
import com.antwerkz.ophelia.models.MongoCommand
import com.antwerkz.ophelia.utils.JacksonMapper
import com.antwerkz.ophelia.utils.MongoUtil
import com.antwerkz.ophelia.utils.Parser
import com.google.common.base.Charsets
import com.mongodb.CommandResult
import com.mongodb.DB
import com.mongodb.DBCollection
import io.dropwizard.views.View
import org.bson.types.ObjectId
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession
import javax.ws.rs.Consumes
import javax.ws.rs.FormParam
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.ResponseBuilder
import java.io.IOException
import java.util.HashMap
import java.util.TreeMap

import java.lang.String.*

Path("/")
Consumes(MediaType.APPLICATION_JSON)
Produces(MediaType.APPLICATION_JSON)
public class QueryResource(private val application: OpheliaApplication, private val mongoCommandDao: MongoCommandDao, private val mongoUtil: MongoUtil) {

    private val mapper = JacksonMapper()

    public fun getDatabaseNames(): List<String> {
        return application.mongo!!.getDatabaseNames()
    }

    private fun generateContent(session: HttpSession, queryResults: QueryResults): QueryResults {
        try {
            queryResults.databaseList = getDatabaseNames()
            val info = getConnectionInfo(session)
            var database = info.database
            if (database == null) {
                database = queryResults.databaseList.get(0)
                info.database = database
            }
            if (info.collection != null) {
                queryResults.setCollectionStats(getCollectionStats(info))
            }

            queryResults.info = info
            queryResults.collections = loadCollections(info)
            return queryResults
        } catch (e: Exception) {
            logger.error(e.getMessage(), e)
            e.printStackTrace()
        }

        return queryResults
    }

    private fun getCollectionStats(info: ConnectionInfo): Map<Any, Any> {
        val collection = application.mongo!!.getDB(info.database).getCollection(info.collection)
        return collection.getStats()
    }

    private fun loadBookmarks(database: String): List<MongoCommand> {
        return mongoCommandDao.findAll(database)
    }

    private fun loadCollections(info: ConnectionInfo): Map<String, Any> {
        val map = TreeMap<String, Any>()
        val db = getDB(info.database)
        if (db != null) {
            for (collection in db.getCollectionNames()) {
                map.put(collection, db.getCollection(collection).getStats().get("count"))
            }
        }
        return map
    }

    GET
    Produces("text/html;charset=ISO-8859-1")
    public fun index(): View {
        return object : View("/index.ftl", Charsets.ISO_8859_1)
    }

    GET
    Produces("text/html;charset=ISO-8859-1")
    Path("/{name}.html")
    public fun html(PathParam("name") name: String): View {
        return object : View(format("/%s.ftl", name), Charsets.ISO_8859_1)
    }

    GET
    Path("/content")
    Produces(MediaType.APPLICATION_JSON)
    throws(javaClass<IOException>())
    public fun content(Context request: HttpServletRequest): String {
        return mapper.writeValueAsString(generateContent(request.getSession(), QueryResults()))
    }

    GET
    Path("/database/{database}")
    Produces(MediaType.APPLICATION_JSON)
    throws(javaClass<IOException>())
    public fun database(Context request: HttpServletRequest, PathParam("database") database: String): String {
        val session = request.getSession()
        val connectionInfo = getConnectionInfo(session)
        connectionInfo.database = database
        connectionInfo.collection = null
        return mapper.writeValueAsString(generateContent(session, QueryResults()))
    }

    GET
    Path("/host/{host}/{port}")
    Produces(MediaType.APPLICATION_JSON)
    throws(javaClass<IOException>())
    public fun changeHost(Context request: HttpServletRequest, PathParam("host") host: String, PathParam("port") port: Int?): String {
        val info = getConnectionInfo(request.getSession())
        info.host = host
        info.port = port
        return content(request)
    }

    POST
    Path("/collectionInfo")
    Produces(MediaType.APPLICATION_JSON)
    throws(javaClass<IOException>())
    public fun collectionInfo(Context request: HttpServletRequest, name: String): String {
        val connectionInfo = getConnectionInfo(request.getSession())
        connectionInfo.collection = name
        return mapper.writeValueAsString(getCollectionInfo(connectionInfo))
    }

    private fun getCollectionInfo(connectionInfo: ConnectionInfo): Map<Any, Any> {
        val collection = application.mongo!!.getDB(connectionInfo.database).getCollection(connectionInfo.collection)
        val map = HashMap()
        val stats = collection.getStats()
        for (name in UNUSED_STATS) {
            stats.remove(name)
        }
        map.put("collectionStats", stats)
        map.put("indexes", collection.getIndexInfo())
        return map
    }

    POST
    Path("/query")
    Produces(MediaType.APPLICATION_JSON)
    throws(javaClass<IOException>())
    public fun query(Context request: HttpServletRequest, mongoCommand: MongoCommand): String {
        val queryResults: QueryResults
        try {
            queryResults = QueryResults()
            generateContent(request.getSession(), queryResults)
            queryResults.dbResults = mongoUtil.query(mongoCommand)
            if (mongoCommand.showCount!!) {
                queryResults.resultCount = mongoUtil.count(mongoCommand)
            }
        } catch (e: Exception) {
            logger.error(e.getMessage(), e)
            queryResults = QueryResults()
            queryResults.error = e.getMessage()
        }

        return mapper.writeValueAsString(queryResults)
    }

    POST
    Path("/export")
    Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Produces(MediaType.APPLICATION_JSON)
    throws(javaClass<IOException>())
    public fun export(Context request: HttpServletRequest, FormParam("query") queryString: String, FormParam("database") database: String, FormParam("collection") collection: String): Response {
        try {
            val mongoCommand = MongoCommand()/*query*/
            mongoCommand.namespace(database, collection)
            val parser = Parser(mongoCommand)
            parser.setLimit(null)
            val response = Response.ok(parser.export(getDB(mongoCommand.database)))
            response.type(MediaType.APPLICATION_JSON)
            response.header("Content-Disposition", format("attachment; filename=\"%s.json\"", parser.getCollection()))
            return response.build()
        } catch (e: Exception) {
            logger.error(e.getMessage(), e)
            return Response.serverError().build()
        }

    }

    POST
    Path("/explain")
    Produces(MediaType.APPLICATION_JSON)
    throws(javaClass<IOException>())
    public fun explain(Context request: HttpServletRequest, mongoCommand: MongoCommand): QueryResults {
        val queryResults: QueryResults
        try {
            queryResults = QueryResults()
            generateContent(request.getSession(), queryResults)
            queryResults.dbResults = mongoUtil.explain(mongoCommand)
        } catch (e: Exception) {
            logger.error(e.getMessage(), e)
            queryResults = QueryResults()
            queryResults.error = e.getMessage()
        }

        return queryResults
    }

    GET
    Path("/deleteBookmark/{id}")
    Produces(MediaType.APPLICATION_JSON)
    public fun deleteBookmark(Context request: HttpServletRequest, PathParam("id") id: String): QueryResults {
        val queryResults = QueryResults()
        System.out.println("id = " + id)
        try {
            mongoCommandDao.delete(ObjectId(id))
        } catch (e: IllegalArgumentException) {
            queryResults.error = e.getMessage()
        }

        return generateContent(request.getSession(), queryResults)
    }

    POST
    Path("/bookmark")
    throws(javaClass<IOException>())
    public fun bookmark(Context request: HttpServletRequest, mongoCommand: MongoCommand): QueryResults {
        val queryResults = QueryResults()
        //        Query operation = mapper.readValue(json, Query.class);
        System.out.println("operation = " + mongoCommand)
        try {
            mongoCommandDao.save(mongoCommand)
        } catch (e: Exception) {
            throw RuntimeException(e.getMessage(), e)
        }

        return generateContent(request.getSession(), queryResults)
    }

    private fun getDB(database: String): DB? {
        return application.mongo!!.getDB(database)
    }

    public fun getConnectionInfo(session: HttpSession): ConnectionInfo {
        var info: ConnectionInfo? = session.getAttribute(INFO) as ConnectionInfo
        if (info == null) {
            info = ConnectionInfo()
            session.setAttribute(INFO, info)
        }
        info!!.application = application
        return info
    }

    class object {
        private val logger = LoggerFactory.getLogger(javaClass<QueryResource>())

        private val INFO = "connection-info"
        private val UNUSED_STATS = array("indexSizes", "serverUsed")
    }

}