package models;

public class Query {
    public String query;
    public Integer limit = 100;
    public Boolean readOnly = false;
    public Boolean showCount = true;

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

    public Boolean getShowCount() {
        return showCount;
    }

    public void setShowCount(Boolean showCount) {
        this.showCount = showCount;
    }
}