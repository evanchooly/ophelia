/**
 * Copyright (C) 2012-2014 Justin Lee <jlee@antwerkz.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.antwerkz.ophelia

import com.fasterxml.jackson.annotation.JsonProperty
import io.dropwizard.Configuration
import com.google.common.collect.ImmutableMap;
import java.util

public class OpheliaConfiguration : Configuration() {
    JsonProperty("viewRendererConfiguration")
    var viewRendererConfiguration: ImmutableMap<String, ImmutableMap<String, String>> =
          ImmutableMap.builder<String, ImmutableMap<String, String>>().build()

    JsonProperty("viewRendererConfiguration")
    fun setViewRendererConfiguration(config: Map<String, Map<String, String>> ) {
        var  builder: ImmutableMap.Builder<String, ImmutableMap<String, String>> = ImmutableMap.builder();
        val set = config.entrySet()
        set.forEach { entry ->
            builder.put(entry.getKey(), ImmutableMap.copyOf(entry.getValue()));
        }
        viewRendererConfiguration = builder.build();
    }
}
