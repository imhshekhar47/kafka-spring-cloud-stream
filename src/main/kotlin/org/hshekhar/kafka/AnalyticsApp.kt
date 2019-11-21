package org.hshekhar.kafka

import org.hshekhar.kafka.binding.AnalyticsBinding
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding


@SpringBootApplication
@EnableBinding(AnalyticsBinding::class)
class AnalyticsApp


fun main(args: Array<String>) {
    runApplication<AnalyticsApp>(*args)
}
