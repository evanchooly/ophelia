/**
 * Copyright (C) 2012-2014 Justin Lee <jlee@antwerkz.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.antwerkz.ophelia;

import com.antwerkz.ophelia.controllers.QueryResource;
import com.antwerkz.ophelia.dao.MongoCommandDao;
import com.antwerkz.ophelia.models.ConnectionInfo;
import com.mongodb.MongoClient;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.eclipse.jetty.server.session.SessionHandler;
import org.mongodb.morphia.Morphia;

public class OpheliaApplication extends Application<OpheliaConfiguration> {
    private Morphia morphia;

    private MongoClient mongo;

    public static void main(String[] args) throws Exception {
        new OpheliaApplication().run(args);
    }

    @Override
    public String getName() {
        return "Ophelia";
    }

    @Override
    public void initialize(final Bootstrap<OpheliaConfiguration> bootstrap) {
        bootstrap.addBundle(new ViewBundle());
        bootstrap.addBundle(new AssetsBundle("/assets", "/assets", null, "assets"));
        bootstrap.addBundle(new AssetsBundle("/META-INF/resources/webjars", "/webjars", null, "webjars"));
    }

    @Override
    public void run(final OpheliaConfiguration configuration, final Environment environment) throws Exception {
        morphia = new Morphia();
        morphia.mapPackage(ConnectionInfo.class.getPackage().getName());

        mongo = new MongoClient();

        environment.getApplicationContext().setSessionsEnabled(true);
        environment.getApplicationContext().setSessionHandler(new SessionHandler());

        environment.jersey().register(new QueryResource(this,
                                                        new MongoCommandDao(morphia.createDatastore(mongo, "ophelia"))));
        environment.healthChecks().register("ophelia", new OpheliaHealthCheck());
    }

    public MongoClient getMongo() {
        return mongo;
    }

}
