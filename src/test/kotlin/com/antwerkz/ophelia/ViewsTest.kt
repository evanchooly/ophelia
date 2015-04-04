package com.antwerkz.ophelia

import com.antwerkz.ophelia.views.OpheliaView
import io.dropwizard.testing.DropwizardTestSupport
import io.dropwizard.views.View
import io.dropwizard.views.freemarker.FreemarkerViewRenderer
import net.htmlparser.jericho.Source
import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass
import org.testng.annotations.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.Locale

open class ViewsTest {
    Test
    public fun index() {
        val renderer = FreemarkerViewRenderer()
        val output = ByteArrayOutputStream()

        val opheliaView = OpheliaView("/index.ftl", Charsets.ISO_8859_1)
        renderer.render(opheliaView, Locale.getDefault(), output)
        //        val source = Source(ByteArrayInputStream(output.toByteArray()))
        //        val a = source.getElementById("id")
        //        Assert.assertTrue(, )

    }


    throws(javaClass<IOException>())
    protected fun render(view: View): Source {
        val renderer = FreemarkerViewRenderer()
        val output = ByteArrayOutputStream()
        renderer.render(view, Locale.getDefault(), output)
        return Source(ByteArrayInputStream(output.toByteArray()))
    }
}