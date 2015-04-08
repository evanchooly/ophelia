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
        val response = client.target("${URL}database/test").request().get()
        val status = response.getStatus()
        Assert.assertEquals(status, 200)

        val s = response.readEntity(javaClass<String>())
        val document = Document(JSON.parse(s) as BasicDBObject)
        Assert.assertEquals(document.get("port"), 27017)
        Assert.assertEquals(document.get("host"), "127.0.0.1")
        Assert.assertEquals(document.get("database"), "test")
        Assert.assertEquals(document.get("collection"), "test")
        Assert.assertNotNull(document.get("databases"))
        Assert.assertNotNull(document.get("collections"))
        Assert.assertNotNull(document.get("collectionInfo"))
        Assert.assertFalse((document.get("collectionInfo") as Map<*, *>).isEmpty())
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
        val URL: String = "http://localhost:8081/ophelia/"
    }
}