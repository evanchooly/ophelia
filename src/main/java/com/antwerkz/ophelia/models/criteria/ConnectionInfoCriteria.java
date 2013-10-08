package com.antwerkz.ophelia.models.criteria;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.CriteriaContainer;
import org.mongodb.morphia.query.Query;

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

}
