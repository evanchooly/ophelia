package com.antwerkz.ophelia.controllers;

import com.antwerkz.ophelia.models.ConnectionInfo;
import com.antwerkz.ophelia.models.Query;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;
import java.util.Map;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class QueryResults {
    public Map<String, Object> collections;
    public List<Query> bookmarks;
    public List<String> databaseList;
    public List<Map> dbResults;
    public String error;
    public ConnectionInfo info;
    private Long resultCount;

    public List<Query> getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(List<Query> bookmarks) {
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
