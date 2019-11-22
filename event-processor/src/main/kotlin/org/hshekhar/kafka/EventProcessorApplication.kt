package org.hshekhar.kafka

import org.hshekhar.kafka.binding.PageVisitBinding
import org.hshekhar.kafka.binding.TransactionBinding
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding

@SpringBootApplication
@EnableBinding(PageVisitBinding::class, TransactionBinding::class)
class EventProcessorApplication

fun main(args: Array<String>) {
    runApplication<EventProcessorApplication>(*args)
}