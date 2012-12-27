package com.antwerkz.ophelia.models.criteria;

import com.antwerkz.critter.TypeSafeFieldEnd;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Criteria;
import com.google.code.morphia.query.CriteriaContainer;
import com.google.code.morphia.query.Query;

public class ConnectionInfoCriteria {
    private Query<com.antwerkz.ophelia.models.ConnectionInfo> query;

    public Query<com.antwerkz.ophelia.models.ConnectionInfo> query() {
        return query;
    }

    public ConnectionInfoCriteria(Datastore ds) {
        query = ds.find(com.antwerkz.ophelia.models.ConnectionInfo.class);
    }

    public CriteriaContainer or(Criteria... criteria) {
        return query.or(criteria);
    }

    public CriteriaContainer and(Criteria... criteria) {
        return query.and(criteria);
    }

    public TypeSafeFieldEnd<? extends CriteriaContainer, com.antwerkz.ophelia.models.ConnectionInfo, org.bson.types.ObjectId> _id() {
        return new TypeSafeFieldEnd<>(query, query.criteria("_id"));
    }

    public TypeSafeFieldEnd<? extends CriteriaContainer, com.antwerkz.ophelia.models.ConnectionInfo, java.lang.String> database() {
        return new TypeSafeFieldEnd<>(query, query.criteria("database"));
    }

    public TypeSafeFieldEnd<? extends CriteriaContainer, com.antwerkz.ophelia.models.ConnectionInfo, java.lang.String> host() {
        return new TypeSafeFieldEnd<>(query, query.criteria("host"));
    }

    public TypeSafeFieldEnd<? extends CriteriaContainer, com.antwerkz.ophelia.models.ConnectionInfo, java.lang.Integer> limit() {
        return new TypeSafeFieldEnd<>(query, query.criteria("limit"));
    }

    public TypeSafeFieldEnd<? extends CriteriaContainer, com.antwerkz.ophelia.models.ConnectionInfo, java.lang.Integer> port() {
        return new TypeSafeFieldEnd<>(query, query.criteria("port"));
    }

    public TypeSafeFieldEnd<? extends CriteriaContainer, com.antwerkz.ophelia.models.ConnectionInfo, java.lang.String> queryString() {
        return new TypeSafeFieldEnd<>(query, query.criteria("queryString"));
    }

    public TypeSafeFieldEnd<? extends CriteriaContainer, com.antwerkz.ophelia.models.ConnectionInfo, java.lang.Boolean> readOnly() {
        return new TypeSafeFieldEnd<>(query, query.criteria("readOnly"));
    }

    public TypeSafeFieldEnd<? extends CriteriaContainer, com.antwerkz.ophelia.models.ConnectionInfo, java.lang.Boolean> showCount() {
        return new TypeSafeFieldEnd<>(query, query.criteria("showCount"));
    }
}
