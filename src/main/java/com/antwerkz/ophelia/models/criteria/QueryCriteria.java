package com.antwerkz.ophelia.models.criteria;

import com.antwerkz.critter.TypeSafeFieldEnd;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Criteria;
import com.google.code.morphia.query.CriteriaContainer;
import com.google.code.morphia.query.Query;

public class QueryCriteria {
    private Query<com.antwerkz.ophelia.models.Query> query;

    public Query<com.antwerkz.ophelia.models.Query> query() {
        return query;
    }

    public QueryCriteria(Datastore ds) {
        query = ds.find(com.antwerkz.ophelia.models.Query.class);
    }

    public CriteriaContainer or(Criteria... criteria) {
        return query.or(criteria);
    }

    public CriteriaContainer and(Criteria... criteria) {
        return query.and(criteria);
    }

    public TypeSafeFieldEnd<? extends CriteriaContainer, com.antwerkz.ophelia.models.Query, org.bson.types.ObjectId> _id() {
        return new TypeSafeFieldEnd<>(query, query.criteria("_id"));
    }

    public TypeSafeFieldEnd<? extends CriteriaContainer, com.antwerkz.ophelia.models.Query, java.lang.String> bookmark() {
        return new TypeSafeFieldEnd<>(query, query.criteria("bookmark"));
    }

    public TypeSafeFieldEnd<? extends CriteriaContainer, com.antwerkz.ophelia.models.Query, java.lang.String> database() {
        return new TypeSafeFieldEnd<>(query, query.criteria("database"));
    }

    public TypeSafeFieldEnd<? extends CriteriaContainer, com.antwerkz.ophelia.models.Query, java.lang.String> queryString() {
        return new TypeSafeFieldEnd<>(query, query.criteria("queryString"));
    }
}
