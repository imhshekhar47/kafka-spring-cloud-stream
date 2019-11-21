package org.hshekhar.kafka.processor

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.common.utils.Bytes
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.kstream.Grouped
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.processor.StateStore
import org.apache.kafka.streams.state.KeyValueStore
import org.hshekhar.kafka.binding.BindingConstant
import org.hshekhar.kafka.binding.PageVisitBinding
import org.hshekhar.kafka.model.PageVisit
import org.hshekhar.kafka.model.PageVisitSerde
import org.hshekhar.kafka.model.SessionState
import org.hshekhar.kafka.model.SessionStateSerde
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Component

@Component
class PageVisitProcessor(val bindingUser: PageVisitBinding) {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(PageVisitProcessor::class.java)
    }

    @StreamListener
    @SendTo(BindingConstant.PAGE_VISIT_OUT_SESSION_STATE)
    fun getUserSessionCountStream(@Input(BindingConstant.PAGE_VISIT_IN) events: KStream<String, PageVisit>): KStream<String, SessionState> {
        LOGGER.trace("entry: processForUserCount(events)")

        val userCountTable = events
                .filter { _, pageVisit -> listOf("login", "logout").contains(pageVisit.pageId) }
                .groupByKey()
                .reduce { _, newVal -> newVal }
                .mapValues(
                        { pageVisit -> SessionState(userId = pageVisit.userId, state = if ("logout" == pageVisit.pageId) "inactive" else "active") },
                        Materialized
                                .`as`<String, SessionState, KeyValueStore<Bytes, ByteArray>>(BindingConstant.MV_ACTIVE_SESSION)
                                .withKeySerde(Serdes.StringSerde())
                                .withValueSerde(SessionStateSerde())
                )

        LOGGER.trace("exit: processForUserCount()")

        return userCountTable.toStream()
    }
}