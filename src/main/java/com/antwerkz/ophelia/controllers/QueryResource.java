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
package com.antwerkz.ophelia.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.antwerkz.ophelia.OpheliaApplication;
import com.antwerkz.ophelia.dao.MongoCommandDao;
import com.antwerkz.ophelia.models.ConnectionInfo;
import com.antwerkz.ophelia.models.MongoCommand;
import com.antwerkz.ophelia.utils.JacksonMapper;
import com.antwerkz.ophelia.utils.Parser;
import com.google.common.base.Charsets;
import com.mongodb.DB;
import io.dropwizard.views.View;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class QueryResource {
  private static final Logger logger = LoggerFactory.getLogger(QueryResource.class);

  private static final String INFO = "connection-info";

  private JacksonMapper mapper = new JacksonMapper();

  private OpheliaApplication application;

  private MongoCommandDao mongoCommandDao;

  public QueryResource(OpheliaApplication application, MongoCommandDao mongoCommandDao) {
    this.application = application;
    this.mongoCommandDao = mongoCommandDao;
  }

  public List<String> getDatabaseNames() {
    return application.getMongo().getDatabaseNames();
  }

  private QueryResults generateContent(HttpSession session, QueryResults queryResults) {
    try {
      ConnectionInfo info = getConnectionInfo(session);
      String database = info.getDatabase();
      if (database == null) {
        database = getDatabaseNames().get(0);
        info.setDatabase(database);
      }
      queryResults.setDatabaseList(getDatabaseNames());
      queryResults.setInfo(info);
      queryResults.setCollections(loadCollections(info));
      return queryResults;
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      e.printStackTrace();
    }
    return queryResults;
  }

  private List<MongoCommand> loadBookmarks(String database) {
    return mongoCommandDao.findAll(database);
  }

  private Map<String, Object> loadCollections(final ConnectionInfo info) {
    TreeMap<String, Object> map = new TreeMap<>();
    DB db = getDB(info.getDatabase());
    if (db != null) {
      for (String collection : db.getCollectionNames()) {
        map.put(collection, db.getCollection(collection).getStats().get("count"));
      }
    }
    return map;
  }

  @GET
  @Produces("text/html;charset=ISO-8859-1")
  public View index() {
    return new View("/index.ftl", Charsets.ISO_8859_1) {
    };
  }

  @GET
  @Produces("text/html;charset=ISO-8859-1")
  @Path("/query.html")
  public View queryHtml() {
    return new View("/query.ftl", Charsets.ISO_8859_1) {
    };
  }

  @GET
  @Path("/content")
  @Produces(MediaType.APPLICATION_JSON)
  public String content(@Context HttpServletRequest request) throws IOException {
    return mapper.writeValueAsString(generateContent(request.getSession(), new QueryResults()));
  }

  @GET()
  @Path("/database/{database}")
  @Produces(MediaType.APPLICATION_JSON)
  public String database(@Context HttpServletRequest request, @PathParam("database") String database)
      throws IOException {
    HttpSession session = request.getSession();
    getConnectionInfo(session).setDatabase(database);
    return mapper.writeValueAsString(generateContent(session, new QueryResults()));
  }

  @GET()
  @Path("/host/{host}/{port}")
  @Produces(MediaType.APPLICATION_JSON)
  public String changeHost(@Context HttpServletRequest request, @PathParam("host") String dbHost,
      @PathParam("port") Integer dbPort) throws IOException {
    ConnectionInfo info = getConnectionInfo(request.getSession());
    info.setHost(dbHost);
    info.setPort(dbPort);
    return content(request);
  }

  @POST
  @Path("/query")
  @Produces(MediaType.APPLICATION_JSON)
  public String query(@Context HttpServletRequest request, MongoCommand mongoCommand) throws IOException {
    System.out.println("*************** QueryResource.query");
    QueryResults queryResults;
    try {
      System.out.println("mongoCommand = " + mongoCommand);
      queryResults = new QueryResults();
      generateContent(request.getSession(), queryResults);
      final Parser parser = new Parser(mongoCommand);
      if (mongoCommand.getShowCount()) {
        queryResults.setResultCount(parser.count(getDB(mongoCommand.getDatabase())));
      }
      queryResults.setDbResults(mongoCommand.execute(getDB(mongoCommand.getDatabase())));
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      queryResults = new QueryResults();
      queryResults.setError(e.getMessage());
    }
    return mapper.writeValueAsString(queryResults);
  }

  @POST
  @Path("/export")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  @Produces(MediaType.APPLICATION_JSON)
  public Response export(@Context HttpServletRequest request, @FormParam("queryString") String queryString,
      @FormParam("database") String database)
      throws IOException {
    try {
      MongoCommand mongoCommand = new MongoCommand(queryString);
      mongoCommand.setDatabase(database);
      final Parser parser = new Parser(mongoCommand);
      parser.setLimit(null);
      ResponseBuilder response = Response.ok(parser.export(getDB(mongoCommand.getDatabase())));
      response.type(MediaType.APPLICATION_JSON);
      response.header("Content-Disposition",
          String.format("attachment; filename=\"%s.json\"", parser.getCollection()));
      return response.build();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return Response.serverError().build();
    }
  }

  @POST
  @Path("/explain")
  @Produces(MediaType.APPLICATION_JSON)
  public QueryResults explain(@Context HttpServletRequest request, MongoCommand mongoCommand) throws IOException {
    QueryResults queryResults;
    try {
      queryResults = new QueryResults();
      generateContent(request.getSession(), queryResults);
      queryResults.setDbResults(mongoCommand.explain(getDB(mongoCommand.getDatabase())));
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      queryResults = new QueryResults();
      queryResults.setError(e.getMessage());
    }
    return queryResults;
  }

  @GET
  @Path("/deleteBookmark/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public QueryResults deleteBookmark(@Context HttpServletRequest request, @PathParam("id") String id) {
    QueryResults queryResults = new QueryResults();
    System.out.println("id = " + id);
    try {
      mongoCommandDao.delete(new ObjectId(id));
    } catch (IllegalArgumentException e) {
      queryResults.setError(e.getMessage());
    }
    return generateContent(request.getSession(), queryResults);
  }

  @POST
  @Path("/bookmark")
  public QueryResults bookmark(@Context HttpServletRequest request, MongoCommand mongoCommand) throws IOException {
    QueryResults queryResults = new QueryResults();
//        Query query = mapper.readValue(json, Query.class);
    System.out.println("query = " + mongoCommand);
    try {
      mongoCommandDao.save(mongoCommand);
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
    return generateContent(request.getSession(), queryResults);
  }

  private DB getDB(String database) {
    return application.getMongo().getDB(database);
  }

  public ConnectionInfo getConnectionInfo(HttpSession session) {
    ConnectionInfo info = (ConnectionInfo) session.getAttribute(INFO);
    if (info == null) {
      info = new ConnectionInfo();
      session.setAttribute(INFO, info);
    }
    info.setApplication(application);
    return info;
  }

}