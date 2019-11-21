package org.hshekhar.kafka.processor

import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.Materialized
import org.hshekhar.kafka.PAGE_COUNT_MV
import org.hshekhar.kafka.PAGE_COUNT_OUT
import org.hshekhar.kafka.PAGE_VIEW_IN
import org.hshekhar.kafka.event.PageViewEvent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Component

@Component
class PageViewEventProcessor {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(PageViewEventProcessor::class.java)
    }

    @StreamListener
    @SendTo(PAGE_COUNT_OUT)
    fun process(@Input(PAGE_VIEW_IN) events: KStream<String, PageViewEvent>): KStream<String, Long> {
        val pageCountStream = events.filter { key, value -> value.duration > 5 }
                .map { key, value -> KeyValue(value.page, "1") }
                .groupByKey()
                .count(Materialized.`as`(PAGE_COUNT_MV))
                .toStream()

        return pageCountStream
    }
}