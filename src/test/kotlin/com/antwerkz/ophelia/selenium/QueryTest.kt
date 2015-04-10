package com.antwerkz.ophelia.selenium

import com.antwerkz.ophelia.TestBase
import org.openqa.selenium.*
import org.openqa.selenium.firefox.FirefoxBinary
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.WebDriverWait
import org.testng.Assert
import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

public class QueryTest : TestBase() {
    //    private var service : ChromeDriverService = ChromeDriverService.Builder()
    //             .usingDriverExecutable(File("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome"))
    //             .usingPort(46000)
    //             .build()
    var driver: FirefoxDriver? = null

    BeforeClass
    fun startDriver() {
        driver = FirefoxDriver()
    }

    AfterClass
    fun stopDriver() {
        driver?.close()
    }

    Test
    fun index() {
        //        println("service.isRunning() = ${service.isRunning()}")
        //        val driver = RemoteWebDriver(service.getUrl(),
        //              DesiredCapabilities.chrome())

        driver?.get("http://localhost:8081/ophelia");

        getById(driver, "db-list")
            ?.click()

        Assert.assertNotNull(getById(driver, "database-test"))
    }

    fun getByName(driver: WebDriver?, name: String): WebElement? {
        return waitForGet(driver, name, By::name)
    }

    fun getById(driver: WebDriver?, id: String): WebElement? {
        return waitForGet(driver, id, By::id)
    }

    fun waitForGet(driver: WebDriver?, value: String, lookup: (String) -> By): WebElement? {
        try {
            return WebDriverWait(driver, 2).until(object : ExpectedCondition<WebElement> {
                override fun apply(d: WebDriver): WebElement {
                    return d.findElement(lookup(value))
                }
            });
        } catch(e: TimeoutException) {
            (driver as TakesScreenshot).getScreenshotAs(PngOutputType("target/missing-ID-${value}.png"));
            return null;
        }
    }
}

class PngOutputType(private val name: String) : OutputType<File> {
    override fun convertFromBase64Png(base64Png: String): File {
        return save(OutputType.BYTES.convertFromBase64Png(base64Png))
    }

    override fun convertFromPngBytes(data: ByteArray): File {
        return save(data)
    }

    private fun save(data: ByteArray): File {
        try {
            val file = File(name)

            file.writeBytes(data)

            return file
        } catch (e: IOException) {
            throw WebDriverException(e)
        }
    }
}
