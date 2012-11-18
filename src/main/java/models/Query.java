package models;

import java.util.List;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.annotations.Entity;
import dao.Finder;
import dao.MongoModel;
import org.bson.types.ObjectId;

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

  public static class QueryFinder extends Finder<Query> {
    public QueryFinder() {
      super(Query.class);
    }

    public Query byBookmarkAndDatabase(final String bookmark, final String database) {
      return mongo(new Operation<Query>() {
        @Override
        public Query execute(Datastore ds) {
          com.google.code.morphia.query.Query<Query> query = ds.createQuery(Query.class);
          query.and(
            query.criteria("bookmark").equal(bookmark),
            query.criteria("database").equal(database)
          );
          return query.get();
        }
      });
    }

    public List<Query> findAll(final String database) {
      return mongo(new Operation<List<Query>>() {
        @Override
        public List<Query> execute(Datastore ds) {
          return ds.createQuery(Query.class).field("database").equal(database).asList();
        }
      });
    }

    public void delete(final ObjectId id) {
      mongo(new Operation<Void>() {
        @Override
        public Void execute(Datastore ds) {
          com.google.code.morphia.query.Query<Query> query = ds.createQuery(Query.class).field("_id").equal(id);
          ds.delete(query);
          return null;
        }
      });
    }
  }
}