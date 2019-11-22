package org.hshekhar.kafka.emitter

import org.hshekhar.kafka.binding.TransactionBinding
import org.hshekhar.kafka.model.Transaction
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@Component
class TransactionEmitter(val binding: TransactionBinding): CommandLineRunner {
    @Value("\${emitter.scheduler.pool-size:1}")
    private var poolSize: Int = 1

    @Value("\${emitter.scheduler.period:2}")
    private var period: Long = 2

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(TransactionEmitter::class.java)
        val seedData = SeedData()
    }

    private val transactionChannel = binding.transactionOutChannel()

    override fun run(vararg args: String?) {
        LOGGER.trace("entry: run()")
        val runnable = Runnable {
            val randomStock = seedData.getRandomStock()
            val transaction = Transaction(
                    userId = seedData.pickRandomUserId(),
                    stockId = randomStock.first,
                    price = randomStock.second,
                    units = Random.nextInt(1,15)
                    )

            val message = MessageBuilder
                    .withPayload(transaction)
                    .setHeader(KafkaHeaders.MESSAGE_KEY, transaction.stockId.toByteArray())
                    .build()

            try {
                transactionChannel.send(message)
                LOGGER.debug("message sent successfully: $message")
            } catch (e: Exception) {
                LOGGER.error("message sent failed:", e)
            }
        }

        LOGGER.trace("Scheduling event emitter [pool=$poolSize, period=$period]")
        Executors.newScheduledThreadPool(poolSize)
                .scheduleAtFixedRate(runnable, 5, period, TimeUnit.SECONDS)
        LOGGER.trace("exit: run()")
    }
}