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

import io.dropwizard.setup.Bootstrap;
import org.mongodb.morphia.Morphia
import io.dropwizard.Application
import com.mongodb.MongoClient
import io.dropwizard.views.ViewBundle
import io.dropwizard.assets.AssetsBundle
import io.dropwizard.setup.Environment
import com.antwerkz.ophelia.models.ConnectionInfo
import com.antwerkz.ophelia.utils.MongoUtil
import com.antwerkz.ophelia.resources.QueryResource
import com.antwerkz.ophelia.dao.MongoCommandDao
import org.eclipse.jetty.server.session.SessionHandler

class OpheliaApplication : Application<OpheliaConfiguration>() {
    val morphia: Morphia = Morphia()

    public var mongo: MongoClient = MongoClient()

    override
    fun getName(): String {
        return "Ophelia";
    }

    override
    fun initialize(bootstrap: Bootstrap<OpheliaConfiguration>) {
        bootstrap.addBundle(ViewBundle());
        bootstrap.addBundle(AssetsBundle("/assets", "/assets", null, "assets"));
        bootstrap.addBundle(AssetsBundle("/META-INF/resources/webjars", "/webjars", null, "webjars"));
    }

    override
    fun run(configuration: OpheliaConfiguration, environment: Environment)  {
        morphia.mapPackage(javaClass<ConnectionInfo>().getPackage().getName()) ;

        mongo = MongoClient() ;

        val mongoUtil = MongoUtil(mongo) ;

        environment.getApplicationContext().setSessionsEnabled(true) ;
        environment.getApplicationContext().setSessionHandler(SessionHandler()) ;

        environment.jersey().register(QueryResource(this,
                MongoCommandDao(morphia.createDatastore(mongo, "ophelia")), mongoUtil) );
        environment.healthChecks().register("ophelia", OpheliaHealthCheck()) ;
    }
}

throws(javaClass<Exception>())
fun main(args: Array<String>) {
    OpheliaApplication().run(args) ;
}
