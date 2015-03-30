package com.antwerkz.ophelia.views

import com.antwerkz.sofia.Ophelia
import io.dropwizard.views.View
import java.nio.charset.Charset

open class OpheliaView(file: String, charset: Charset) : View(file, charset){
    public fun ophelia(): Ophelia {
        return Ophelia()
    }

}