package org.hshekhar.kafka.emitter

import org.hshekhar.kafka.binding.SessionBinding
import org.hshekhar.kafka.model.PageVisit
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
class PageVisitEventEmitter(val binding: SessionBinding) : CommandLineRunner {

    @Value("\${emitter.scheduler.pool-size:1}")
    private var poolSize: Int = 1

    @Value("\${emitter.scheduler.period:2}")
    private var period: Long = 2

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(PageVisitEventEmitter::class.java)

        private val userIds = arrayOf("alfred", "anton", "aston", "angela", "alice", "bryan", "brandon", "billy", "caleb", "clive", "cynthea", "david", "dustan")
        private val pageIds = arrayOf("login", "logout", "index", "home", "blog", "products", "documentation", "partners", "career", "about", "search", "contact")
    }

    private val pvEventMessageChannel = binding.pageVisitOutChannel()

    private fun getRandomUserId(): String {
        return "user_${Random.nextInt(0, 100)}"
    }

    private fun chooseOneFrom(items: Array<String>): String {
        return items[Random.nextInt(0, items.size)]
    }

    override fun run(vararg args: String?) {
        LOGGER.trace("entry: run(${args.joinToString(",")})")

        val runnable = Runnable {
            val pvEvent = PageVisit(
                    userId = chooseOneFrom(userIds),
                    pageId = chooseOneFrom(pageIds),
                    duration = Random.nextLong(0, 10))

            val message = MessageBuilder
                    .withPayload(pvEvent)
                    .setHeader(KafkaHeaders.MESSAGE_KEY, pvEvent.userId.toByteArray())
                    .build()

            try {
                pvEventMessageChannel.send(message)
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