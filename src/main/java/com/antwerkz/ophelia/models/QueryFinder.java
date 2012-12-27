package com.antwerkz.ophelia.models;

import com.antwerkz.ophelia.dao.Finder;
import com.antwerkz.ophelia.dao.MongoModel;
import com.antwerkz.ophelia.dao.MongoModel.Operation;
import com.antwerkz.ophelia.models.criteria.QueryCriteria;
import com.google.code.morphia.Datastore;
import org.bson.types.ObjectId;

import java.util.List;

public class QueryFinder extends Finder<Query> {
    public QueryFinder() {
        super(Query.class);
    }

    public Query byBookmarkAndDatabase(final String bookmark, final String database) {
        return MongoModel.mongo(new Operation<Query>() {
            @Override
            public Query execute(Datastore ds) {
                QueryCriteria criteria = new QueryCriteria(ds);
                criteria.and(
                        criteria.bookmark().equal(bookmark),
                        criteria.database().equal(database)
                );
                return criteria.query().get();
            }
        });
    }

    public List<Query> findAll(final String database) {
        return MongoModel.mongo(new Operation<List<Query>>() {
            @Override
            public List<Query> execute(Datastore ds) {
                QueryCriteria criteria = new QueryCriteria(ds);
                criteria.database().equal(database);
                return criteria.query().asList();
            }
        });
    }

    public void delete(final ObjectId id) {
        MongoModel.mongo(new Operation<Void>() {
            @Override
            public Void execute(Datastore ds) {
                QueryCriteria criteria = new QueryCriteria(ds);
                criteria._id().equal(id);
                ds.delete(criteria.query());
                return null;
            }
        });
    }
}
