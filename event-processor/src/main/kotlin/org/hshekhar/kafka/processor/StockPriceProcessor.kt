package org.hshekhar.kafka.processor

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.common.utils.Bytes
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.state.KeyValueStore
import org.hshekhar.kafka.binding.BindingConstant
import org.hshekhar.kafka.model.Stock
import org.hshekhar.kafka.model.StockSerde
import org.hshekhar.kafka.model.Transaction
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Component

@Component
class StockPriceProcessor {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(StockPriceProcessor::class.java)
    }

    @StreamListener
    @SendTo(BindingConstant.TRANSACTION_OUT_STOCK)
    fun getStockPriceFromTransactions(@Input(value = BindingConstant.TRANSACTION_IN) events: KStream<String, Transaction>): KStream<String, Stock> {
        LOGGER.trace("entry: getStockPriceFromTransactions()")
        val stockPriceTable = events
                .groupByKey()
                .reduce { _, latestValue ->  latestValue}
                .mapValues(
                    { transaction -> Stock(id = transaction.stockId, price = transaction.price)},
                        Materialized.`as`<String, Stock, KeyValueStore<Bytes, ByteArray>>(BindingConstant.MV_TRANSACTION)
                                .withKeySerde(Serdes.StringSerde())
                                .withValueSerde(StockSerde())
                )
        LOGGER.trace("exit: getStockPriceFromTransactions()")

        return stockPriceTable.toStream()
    }
}