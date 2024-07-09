/*
 *	Copyright 2024 cufy.org
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *	    http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
package org.cufy.kafka.routing

import org.slf4j.LoggerFactory

fun embeddedConsumer(module: KafkaRoute.() -> Unit): KafkaEngine {
    @OptIn(ExperimentalKafkaRoutingAPI::class)
    return embeddedConsumer(
        log = LoggerFactory.getLogger("kafka.application"),
        rootTopic = "",
        developmentMode = false,
        configure = {},
        module = module,
    )
}

@ExperimentalKafkaRoutingAPI
fun embeddedConsumer(
    log: Logger,
    rootTopic: String,
    developmentMode: Boolean,
    configure: KafkaEngine.Configuration .() -> Unit = {},
    module: KafkaRoute.() -> Unit,
): KafkaEngine {
    val environment = SimpleKafkaEnvironment(
        log = log,
        rootTopic = rootTopic,
        developmentMode = developmentMode,
    )

    return SimpleKafkaEngine(
        environment,
        listOf(module),
        configure,
    )
}
