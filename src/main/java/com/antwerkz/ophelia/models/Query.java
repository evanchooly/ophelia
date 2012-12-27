package com.antwerkz.ophelia.models;

import com.antwerkz.ophelia.dao.MongoModel;
import com.google.code.morphia.annotations.Entity;

@Entity("queries")
public class Query extends MongoModel<Query> {
    private String bookmark;
    private String database;
    private String queryString;

    public String getBookmark() {
        return bookmark;
    }

    public void setBookmark(String bookmark) {
        this.bookmark = bookmark;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Query query = (Query) o;
        if (bookmark != null ? !bookmark.equals(query.bookmark) : query.bookmark != null) {
            return false;
        }
        if (queryString != null ? !queryString.equals(query.queryString) : query.queryString != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = bookmark != null ? bookmark.hashCode() : 0;
        result = 31 * result + (queryString != null ? queryString.hashCode() : 0);
        return result;
    }

    public static QueryFinder finder() {
        return new QueryFinder();
    }

}