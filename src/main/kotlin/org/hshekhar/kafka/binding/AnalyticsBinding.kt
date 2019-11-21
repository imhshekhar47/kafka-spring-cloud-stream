package org.hshekhar.kafka.binding

import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.KTable
import org.hshekhar.kafka.PAGE_COUNT_IN
import org.hshekhar.kafka.PAGE_COUNT_OUT
import org.hshekhar.kafka.PAGE_VIEW_IN
import org.hshekhar.kafka.PAGE_VIEW_OUT
import org.hshekhar.kafka.event.PageViewEvent
import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.MessageChannel

interface AnalyticsBinding {

    @Input(PAGE_COUNT_IN)
    fun pageCountIn(): KTable<String, Long>

    @Output(PAGE_COUNT_OUT)
    fun pageCountOut(): KStream<String, Long>

    @Input(PAGE_VIEW_IN)
    fun pageViewIn(): KStream<String, PageViewEvent>

    @Output(PAGE_VIEW_OUT)
    fun pageViewOut(): MessageChannel
}