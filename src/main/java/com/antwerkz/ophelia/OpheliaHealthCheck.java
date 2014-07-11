package com.antwerkz.ophelia;

import com.codahale.metrics.health.HealthCheck;

public class OpheliaHealthCheck extends HealthCheck {
    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
