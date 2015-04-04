package com.antwerkz.ophelia

import io.dropwizard.testing.DropwizardTestSupport
import io.dropwizard.testing.ResourceHelpers.resourceFilePath
import org.glassfish.jersey.client.ClientResponse
import org.testng.annotations.*
import javax.ws.rs.client
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.Response
import kotlin.test.assertEquals

class QueryResourceTest {
    private var client: Client = ClientBuilder.newClient()

    companion object {
        val dropwizard: DropwizardTestSupport<OpheliaConfiguration> = DropwizardTestSupport(javaClass<OpheliaApplication>(),
                "ophelia.yml");
    }

    BeforeClass
    fun setUp() {
        dropwizard.before();
    }

    AfterClass
    fun tearDown() {
        dropwizard.after();
    }

    AfterMethod
    throws(javaClass<Exception>())
    public fun reset() {
        client = ClientBuilder.newClient()
    }

    Test
    fun index() {
        val response = client.target("http://localhost:8081/ophelia").request().get()
        val s = response.readEntity(javaClass<String>())
        println(s)
        val status = response.getStatus()
        assertEquals(status, 200)
    }
}