package com.antwerkz.ophelia

import com.codahale.metrics.health.HealthCheck

public class OpheliaHealthCheck : HealthCheck() {
    throws(javaClass<Exception>())
    override fun check(): HealthCheck.Result {
        return HealthCheck.Result.healthy()
    }
}
