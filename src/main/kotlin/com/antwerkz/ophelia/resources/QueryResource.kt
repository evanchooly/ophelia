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
package com.antwerkz.ophelia.resources

import com.antwerkz.ophelia.OpheliaApplication
import com.antwerkz.ophelia.dao.MongoCommandDao
import com.antwerkz.ophelia.models.ConnectionInfo
import com.antwerkz.ophelia.models.MongoCommand
import com.antwerkz.ophelia.models.QueryResults
import com.antwerkz.ophelia.utils.JacksonMapper
import com.antwerkz.ophelia.utils.MongoUtil
import com.antwerkz.ophelia.utils.Parser
import com.antwerkz.ophelia.views.OpheliaView
import com.google.common.base.Charsets
import org.slf4j.LoggerFactory
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpSession
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

Path("/")
Consumes(MediaType.APPLICATION_JSON)
Produces(MediaType.APPLICATION_JSON)
public class QueryResource(private val application: OpheliaApplication, private val mongoUtil: MongoUtil) {

    private val mapper = JacksonMapper()

    GET
    Produces("text/html;charset=ISO-8859-1")
    public fun index(): OpheliaView {
        return html("index")
    }

    GET
    Produces("text/html;charset=ISO-8859-1")
    Path("/{name}.html")
    public fun html(PathParam("name") name: String): OpheliaView {
        return OpheliaView("/${name}.ftl".toString(), Charsets.ISO_8859_1)
    }

    GET
    Path("/database/{database}")
    Produces(MediaType.APPLICATION_JSON)
    throws(javaClass<IOException>())
    public fun database(Context request: HttpServletRequest, PathParam("database") database: String): String {
        val session = request.getSession()
        var info = getConnectionInfo(session)
        info = ConnectionInfo(application, info.host, info.port, database, info.collection)
        session.setAttribute(INFO, info)
        return mapper.writeValueAsString(info)
    }

    GET
    Path("/host/{host}/{port}")
    Produces(MediaType.APPLICATION_JSON)
    throws(javaClass<IOException>())
    public fun changeHost(Context request: HttpServletRequest, PathParam("host") host: String, PathParam("port") port: Int?): String {
        var info = getConnectionInfo(request.getSession())
        info = ConnectionInfo(application, host, port ?: 27017, info.database, info.collection);
        request.getSession().setAttribute(INFO, info)
        return mapper.writeValueAsString(info)
    }

    POST
    Path("/collectionInfo")
    Produces(MediaType.APPLICATION_JSON)
    throws(javaClass<IOException>())
    public fun collectionInfo(Context request: HttpServletRequest, name: String): String {
        var info = getConnectionInfo(request.getSession())
        info = ConnectionInfo(application, info.host, info.port, info.database, name);
        request.getSession().setAttribute(INFO, info)
        return mapper.writeValueAsString(info)
    }

    POST
    Path("/query")
    Produces(MediaType.APPLICATION_JSON)
    throws(javaClass<IOException>())
    public fun query(Context request: HttpServletRequest, mongoCommand: MongoCommand): String {
        val queryResults: QueryResults
        try {
            queryResults = QueryResults(dbResults = mongoUtil.query(mongoCommand))
            if (mongoCommand.showCount) {
                queryResults.resultCount = mongoUtil.count(mongoCommand)
            }
        } catch (e: Exception) {
            logger.error(e.getMessage(), e)
            queryResults = QueryResults()
            queryResults.error = e.getMessage()!!
        }

        return mapper.writeValueAsString(queryResults)
    }

    POST
    Path("/export")
    Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Produces(MediaType.APPLICATION_JSON)
    throws(javaClass<IOException>())
    public fun export(Context request: HttpServletRequest, FormParam("query") queryString: String, FormParam("database") database: String,
                      FormParam("collection") collection: String): Response {
        try {
            val mongoCommand = MongoCommand()
            mongoCommand.namespace(database, collection)
            val parser = Parser(mongoCommand)
            parser.limit = Integer.MAX_VALUE
            val response = Response.ok(parser.export(application.mongo.getDatabase(getConnectionInfo(request.getSession()).database)))
            response.type(MediaType.APPLICATION_JSON)
            response.header("Content-Disposition", "attachment; filename=\"${parser.collection}.json\"")
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
            queryResults = QueryResults(dbResults = mongoUtil.explain(mongoCommand))
        } catch (e: Exception) {
            logger.error(e.getMessage(), e)
            queryResults = QueryResults(error = e.getMessage()!!)
        }

        return queryResults
    }

    /*
        GET
        Path("/deleteBookmark/{id}")
        Produces(MediaType.APPLICATION_JSON)
        public fun deleteBookmark(Context request: HttpServletRequest, PathParam("id") id: String): QueryResults {
            val queryResults = QueryResults()
            System.out.println("id = " + id)
            try {
                mongoCommandDao.delete(ObjectId(id))
            } catch (e: IllegalArgumentException) {
                queryResults.error = e.getMessage()!!
            }

            return generateContent(request.getSession())
        }

        POST
        Path("/bookmark")
        throws(javaClass<IOException>())
        public fun bookmark(Context request: HttpServletRequest, mongoCommand: MongoCommand): QueryResults {
            //        val queryResults = QueryResults()
            //        Query operation = mapper.readValue(json, Query.class);
            System.out.println("operation = " + mongoCommand)
            try {
                mongoCommandDao.save(mongoCommand)
            } catch (e: Exception) {
                throw RuntimeException(e.getMessage(), e)
            }

            return generateContent(request.getSession())
        }

        private fun loadBookmarks(database: String): List<MongoCommand> {
            return mongoCommandDao.findAll(database)
        }
    */

    public fun getConnectionInfo(session: HttpSession): ConnectionInfo {
        var info = session.getAttribute(INFO)
        if (info == null) {
            info = ConnectionInfo(application)
            session.setAttribute(INFO, info)
        }
        return info!! as ConnectionInfo
    }


    companion object {
        private val logger = LoggerFactory.getLogger(javaClass<QueryResource>())

        private val INFO = "connection-info"
        val UNUSED_STATS = array("indexSizes", "serverUsed")
    }

}

