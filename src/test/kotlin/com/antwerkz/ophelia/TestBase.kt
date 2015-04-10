package com.antwerkz.ophelia

import io.dropwizard.testing.DropwizardTestSupport
import org.testng.annotations.AfterClass
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeClass
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder

public open class TestBase() {
    public var client: Client = ClientBuilder.newClient()

    BeforeClass
    fun setUp() {
        dropwizard.before();
        //        service.start()
    }

    AfterClass
    fun tearDown() {
        dropwizard.after();
        //        service.stop()
    }

    AfterMethod
    throws(javaClass<Exception>())
    public fun reset() {
        client = ClientBuilder.newClient()
    }

    public companion object {
        val dropwizard: DropwizardTestSupport<OpheliaConfiguration> = DropwizardTestSupport(
              javaClass<OpheliaApplication>(),
              "ophelia.yml");
        val URL: String = "http://localhost:8081/ophelia"
    }
}