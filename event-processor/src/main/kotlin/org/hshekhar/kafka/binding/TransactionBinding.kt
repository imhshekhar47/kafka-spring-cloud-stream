package org.hshekhar.kafka.binding

import org.apache.kafka.streams.kstream.KStream
import org.hshekhar.kafka.model.Stock
import org.hshekhar.kafka.model.Transaction
import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.Output

interface TransactionBinding {

    @Input(BindingConstant.TRANSACTION_IN)
    fun transactionInStream(): KStream<String, Transaction>

    @Output(BindingConstant.TRANSACTION_OUT_STOCK)
    fun transactionOutStockStream(): KStream<String, Stock>
}