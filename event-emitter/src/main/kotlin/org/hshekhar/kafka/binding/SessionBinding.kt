package org.hshekhar.kafka.binding

import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.MessageChannel

interface SessionBinding {
    @Output(value = BindingConstants.PAGE_VIST_OUT)
    fun pageVisitOutChannel(): MessageChannel
}