package models;

import play.data.validation.Constraints;

import javax.persistence.Column;

public class Query {
    @Constraints.Required
    public String query;
    @Column(name = "queryLimit")
    public Integer limit = 100;
    public Boolean readOnly = false;

    public Integer getLimit() {
        return limit == null || limit < 1 ? 10000 : limit;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly == null ? false : readOnly;
    }
}