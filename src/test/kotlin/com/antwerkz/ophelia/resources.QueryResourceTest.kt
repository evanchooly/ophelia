package com.antwerkz.ophelia

import com.antwerkz.ophelia.utils.pretty
import com.mongodb.BasicDBObject
import com.mongodb.util.JSON
import io.dropwizard.testing.DropwizardTestSupport
import org.bson.Document
import org.bson.json.JsonReader
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

class QueryResourceTest {
    private var client: Client = ClientBuilder.newClient()
    //    private var service : ChromeDriverService = ChromeDriverService.Builder()
    //             .usingDriverExecutable(File("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome"))
    //             .usingPort(46000)
    //             .build()

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

    Test
    fun index() {
        //        println("service.isRunning() = ${service.isRunning()}")
        //        val driver = RemoteWebDriver(service.getUrl(),
        //              DesiredCapabilities.chrome())

        val driver = FirefoxDriver()
        driver.get("http://localhost:8081/ophelia");

        getByName(driver, "query")
              .sendKeys("Cheese!");

        getById(driver, "queryButton")
              .click();
    }

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

    private fun parseResponse(response: Response) = Document(JSON.parse(response.readEntity(javaClass<String>())) as BasicDBObject)

    private fun get(path: String): Response {
        var response = client.target("${URL}${path}").request().get()
        Assert.assertEquals(response.getStatus(), 200)
        return response
    }

    fun getByName(driver: WebDriver, name: String): WebElement {
        WebDriverWait(driver, 10).until(object : ExpectedCondition<Boolean> {
            override fun apply(d: WebDriver): Boolean {
                return d.findElement(By.name(name)) != null
            }
        });

        return driver.findElement(By.name(name));

    }

    fun getById(driver: WebDriver, id: String): WebElement {
        WebDriverWait(driver, 10).until(object : ExpectedCondition<Boolean> {
            override fun apply(d: WebDriver): Boolean {
                return d.findElement(By.id(id)) != null
            }
        });

        return driver.findElement(By.id(id));

    }

    companion object {
        val dropwizard: DropwizardTestSupport<OpheliaConfiguration> = DropwizardTestSupport(javaClass<OpheliaApplication>(),
              "ophelia.yml");
        val URL: String = "http://localhost:8081/ophelia"
    }
}