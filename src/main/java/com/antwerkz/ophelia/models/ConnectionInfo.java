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
package com.antwerkz.ophelia.models;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.antwerkz.ophelia.dao.Finder;
import com.antwerkz.ophelia.plugins.MongOphelia;
import com.mongodb.CommandResult;
import com.mongodb.DB;

public class ConnectionInfo {
    private String database;
    private String host = "127.0.0.1";
    private Integer port = 27017;

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public List<String> getDatabaseNames() {
        return MongOphelia.getDatabaseNames();
    }

    public Map<String, Object> loadCollections() {
        TreeMap<String, Object> map = new TreeMap<>();
        DB db = MongOphelia.getDB();
        if (db != null) {
            Set<String> collections = db.getCollectionNames();
            for (String collection : collections) {
                CommandResult stats = db.getCollection(collection).getStats();
                map.put(collection, stats.get("count"));
            }
        }
        return map;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("ConnectionInfo");
        sb.append("{host='").append(host).append('\'');
        sb.append(", port=").append(port);
        sb.append('}');
        return sb.toString();
    }

    public static Finder<ConnectionInfo> find() {
        return new Finder<ConnectionInfo>(ConnectionInfo.class);
    }
}