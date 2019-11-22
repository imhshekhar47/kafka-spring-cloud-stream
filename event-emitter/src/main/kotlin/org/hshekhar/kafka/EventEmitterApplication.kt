package org.hshekhar.kafka

import org.hshekhar.kafka.binding.SessionBinding
import org.hshekhar.kafka.binding.TransactionBinding
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding

@SpringBootApplication
@EnableBinding(SessionBinding::class, TransactionBinding::class)
class EventEmitterApplication

fun main(args: Array<String>) {
    runApplication<EventEmitterApplication>(*args)
}