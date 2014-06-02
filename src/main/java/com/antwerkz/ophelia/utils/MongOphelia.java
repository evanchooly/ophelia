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
package com.antwerkz.ophelia.utils;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.antwerkz.ophelia.models.ConnectionInfo;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

@WebFilter("/ophelia/*")
public class MongOphelia implements Filter {
  private static ThreadLocal<Mongo> pool = new ThreadLocal<>();

  private static Morphia morphia = new Morphia();

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    morphia.mapPackage(ConnectionInfo.class.getPackage().getName());
    get().ensureIndexes();
  }

  @Override
  public void destroy() {
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    chain.doFilter(request, response);
    if (pool.get() != null) {
      pool.get().close();
      pool.set(null);
    }
  }

  public static Datastore get() {
    return get("ophelia");
  }

  public static Datastore get(String database) {
    return morphia.createDatastore(getMongo(), database);
  }

  public static Mongo getMongo() {
    Mongo mongo = pool.get();
    if (mongo == null) {
      try {
        mongo = new MongoClient();
      } catch (UnknownHostException e) {
        throw new RuntimeException(e.getMessage(), e);
      }
      pool.set(mongo);
    }
    return mongo;
  }

  public static List<String> getDatabaseNames() {
    return get().getDB().getMongo().getDatabaseNames();
  }

  public static void close() {
    Mongo mongo = pool.get();
    if (mongo != null) {
      mongo.close();
      pool.remove();
    }
  }

  public static DB getDB() {
    return get().getDB();
  }
}
