package org.hshekhar.kafka.binding

import org.apache.kafka.streams.kstream.KStream
import org.hshekhar.kafka.model.PageVisit
import org.hshekhar.kafka.model.SessionState
import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.Output


interface PageVisitBinding {

    @Input(value = BindingConstant.PAGE_VISIT_IN)
    fun userSessionInStreamForCount(): KStream<String, PageVisit>

    @Output(value = BindingConstant.PAGE_VISIT_OUT_SESSION_STATE)
    fun userSessionOutStreamState(): KStream<String, SessionState>

}