package org.hshekhar.kafka.binding

import org.springframework.cloud.stream.annotation.Output
import org.springframework.messaging.MessageChannel

interface TransactionBinding {

    @Output(BindingConstants.TRANSACTION_OUT)
    fun transactionOutChannel(): MessageChannel
}