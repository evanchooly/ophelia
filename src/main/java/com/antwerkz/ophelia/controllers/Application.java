/**
 * Copyright (C) 2012-2013 Justin Lee <jlee@antwerkz.com>
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

import com.antwerkz.ophelia.models.ConnectionInfo;
import com.antwerkz.ophelia.models.MongoCommand;
import com.antwerkz.ophelia.plugins.MongOphelia;
import com.mongodb.DB;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("app")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Application {
  private static final Logger logger = LoggerFactory.getLogger(Application.class);

  private static final String INFO = "connection-info";

  private JacksonMapper mapper = new JacksonMapper();

  private QueryResults generateContent(HttpSession session, QueryResults queryResults) {
    try {
      ConnectionInfo info = getConnectionInfo(session);
      String database = info.getDatabase();
      if (database == null) {
        database = MongOphelia.getDatabaseNames().get(0);
        info.setDatabase(database);
      }
      queryResults.setDatabaseList(MongOphelia.getDatabaseNames());
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
    return MongoCommand.finder().findAll(database);
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
  @Path("content")
  @Produces(MediaType.APPLICATION_JSON)
  public QueryResults content(@Context HttpServletRequest request) {
    return generateContent(request.getSession(), new QueryResults());
  }

  @GET()
  @Path("/database/{database}")
  @Produces(MediaType.APPLICATION_JSON)
  public QueryResults database(@Context HttpServletRequest request, @PathParam("database") String database) {
    HttpSession session = request.getSession();
    getConnectionInfo(session).setDatabase(database);
    return generateContent(session, new QueryResults());
  }

  @GET()
  @Path("/host/{host}/{port}")
  @Produces(MediaType.APPLICATION_JSON)
  public QueryResults changeHost(@Context HttpServletRequest request, @PathParam("host") String dbHost,
      @PathParam("port") Integer dbPort) {
    ConnectionInfo info = getConnectionInfo(request.getSession());
    info.setHost(dbHost);
    info.setPort(dbPort);
    return content(request);
  }

  @POST
  @Path("/query")
  @Produces(MediaType.APPLICATION_JSON)
  public QueryResults query(@Context HttpServletRequest request, MongoCommand mongoCommand) throws IOException {
    QueryResults queryResults;
    try {
      queryResults = new QueryResults();
      generateContent(request.getSession(), queryResults);
      final Parser parser = new Parser(mongoCommand);
      if (mongoCommand.getShowCount()) {
        queryResults.setResultCount(parser.count(getDB(mongoCommand.getDatabase())));
      }
      queryResults.setDbResults(parser.execute(getDB(mongoCommand.getDatabase())));
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      queryResults = new QueryResults();
      queryResults.setError(e.getMessage());
    }
    return queryResults;
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
      final Parser parser = new Parser(mongoCommand);
      queryResults.setDbResults(parser.explain(getDB(mongoCommand.getDatabase())));
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
      MongoCommand.finder().delete(new ObjectId(id));
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
      mongoCommand.save();
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage(), e);
    }
    return generateContent(request.getSession(), queryResults);
  }

  private DB getDB(String database) {
    return MongOphelia.get(database).getDB();
  }

  public ConnectionInfo getConnectionInfo(HttpSession session) {
    ConnectionInfo info = (ConnectionInfo) session.getAttribute(INFO);
    if (info == null) {
      info = createConnection(session);
    }
    return info;
  }

  private static ConnectionInfo createConnection(HttpSession session) {
    ConnectionInfo info = new ConnectionInfo();
    session.setAttribute(INFO, info);
    return info;
  }

}