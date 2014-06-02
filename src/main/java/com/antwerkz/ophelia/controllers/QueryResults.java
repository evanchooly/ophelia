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

import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;

import com.antwerkz.ophelia.models.ConnectionInfo;
import com.antwerkz.ophelia.models.MongoCommand;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@XmlRootElement
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class QueryResults {
  public Map<String, Object> collections;

  public List<MongoCommand> bookmarks;

  public List<String> databaseList;

  public List<Map> dbResults;

  public String error;

  public ConnectionInfo info;

  private Long resultCount;

  public List<MongoCommand> getBookmarks() {
    return bookmarks;
  }

  public void setBookmarks(List<MongoCommand> bookmarks) {
    this.bookmarks = bookmarks;
  }

  public Map<String, Object> getCollections() {
    return collections;
  }

  public void setCollections(Map<String, Object> collections) {
    this.collections = collections;
  }

  public List<String> getDatabaseList() {
    return databaseList;
  }

  public void setDatabaseList(List<String> databaseList) {
    this.databaseList = databaseList;
  }

  public List<Map> getDbResults() {
    return dbResults;
  }

  public void setDbResults(List<Map> dbResults) {
    this.dbResults = dbResults;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public ConnectionInfo getInfo() {
    return info;
  }

  public void setInfo(ConnectionInfo info) {
    this.info = info;
  }

  public Long getResultCount() {
    return resultCount;
  }

  public void setResultCount(Long resultCount) {
    this.resultCount = resultCount;
  }
}
