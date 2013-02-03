package com.antwerkz.ophelia.models;

import java.util.Map;

import com.antwerkz.ophelia.dao.MongoModel;
import com.google.code.morphia.annotations.Entity;

@Entity("queries")
public class Query extends MongoModel<Query> {
    private static final int DEFAULT_LIMIT = 100;
    private String bookmark;
    private String database;
    private String queryString;
    private Integer limit;
    private Boolean readOnly;
    private Boolean showCount;
    private Boolean explain;
    private Map<String, String> params;

    public Query() {
        readOnly = false;
        showCount = true;
        explain = false;
        limit = DEFAULT_LIMIT;
    }

    public Query(String queryString) {
        this();
        this.queryString = queryString;
    }

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

    public Integer getLimit() {
        return limit == null || limit < 1 ? DEFAULT_LIMIT : limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = coerce(readOnly);
    }

    public Boolean getShowCount() {
        return showCount;
    }

    public void setShowCount(Boolean showCount) {
        this.showCount = coerce(showCount);
    }

    private boolean coerce(final Boolean value) {
        return value == null ? false : value;
    }

    public Boolean getExplain() {
        return explain;
    }

    public void setExplain(Boolean explain) {
        this.explain = coerce(explain);
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(final Map<String, String> params) {
        this.params = params;
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