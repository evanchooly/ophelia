package com.antwerkz.ophelia.resources

import com.antwerkz.ophelia.OpheliaApplication
import com.antwerkz.ophelia.OpheliaConfiguration
import com.antwerkz.ophelia.TestBase
import com.mongodb.BasicDBObject
import com.mongodb.util.JSON
import io.dropwizard.testing.DropwizardTestSupport
import org.bson.Document
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.WebDriverWait
import org.testng.Assert
import org.testng.annotations.AfterClass
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test
import javax.ws.rs.client.Client
import javax.ws.rs.client.ClientBuilder
import javax.ws.rs.core.Response

class QueryResourceTest : TestBase() {
    Test
    fun database() {
        val response = get("/database/test")
        val document = parseResponse(response)

        testConnectionInfo(document)
    }

    Test
    fun changeHost() {
        var response = get("/host/localhost/27017")
        var document = parseResponse(response)

        testConnectionInfo(document)

        response = get("/host/127.0.0.1/26000")
        document = parseResponse(response)

        testConnectionInfo(document, host = "127.0.0.1", port = 26000)
    }

    private fun testConnectionInfo(document: Document, host: String = "localhost", port: Int = 27017,
                                   database: String = "test", collection: String = "test") {
        Assert.assertEquals(document.get("host"), host)
        Assert.assertEquals(document.get("port"), port)
        Assert.assertEquals(document.get("database"), database)
        Assert.assertEquals(document.get("collection"), collection)
        Assert.assertNotNull(document.get("databases"))
        Assert.assertNotNull(document.get("collections"))
        Assert.assertNotNull(document.get("collectionInfo"))
        Assert.assertFalse((document.get("collectionInfo") as Map<*, *>).isEmpty())
    }

    private fun parseResponse(response: Response) = Document(
          JSON.parse(response.readEntity(javaClass<String>())) as BasicDBObject)

    private fun get(path: String): Response {
        var response = client.target("${TestBase.URL}${path}").request().get()
        Assert.assertEquals(response.getStatus(), 200)
        return response
    }

}