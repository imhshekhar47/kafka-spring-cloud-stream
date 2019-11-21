package org.hshekhar.kafka.emitter

import org.hshekhar.kafka.binding.AnalyticsBinding
import org.hshekhar.kafka.event.PageViewEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@Component
class PageViewEventEmitter(val binding: AnalyticsBinding): ApplicationRunner {
    private val pvoChannel: MessageChannel = binding.pageViewOut()

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(PageViewEventEmitter::class.java)
    }

    override fun run(args: ApplicationArguments?) {
        val users = arrayOf("hshekhar", "johnsnow", "brandon", "robert", "tyrion", "sansa", "ned")
        val pages = arrayOf("index", "blog", "docs", "new", "about", "sitemap")

        val runnable = Runnable {
            val randomUser = users[Random.nextInt(0, users.size)]
            val message = MessageBuilder
                    .withPayload(PageViewEvent(
                            userId = randomUser,
                            page = pages[Random.nextInt(0, pages.size)],
                            duration = Random.nextInt(1, 10)))
                    .setHeader(KafkaHeaders.MESSAGE_KEY, randomUser.toByteArray())
                    .build()
            try {
                pvoChannel.send(message)
                LOGGER.info("sent: ${message.toString()}")
            } catch (e: Exception) {
                LOGGER.error("Message send failed", e)
            }
        }

        Executors
                .newScheduledThreadPool(1)
                .scheduleAtFixedRate(runnable, 1, 1, TimeUnit.SECONDS)
    }
}