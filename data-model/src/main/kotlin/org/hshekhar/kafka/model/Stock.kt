package org.hshekhar.kafka.model

import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serializer
import java.util.*

class Stock(val id: String, val price: Double, val timestamp: Date = Date())

class StockDeserializer : Deserializer<Stock> {
    override fun deserialize(topic: String?, data: ByteArray?): Stock? {
        return if (null == data) data else jsonMapper.readValue(data, Stock::class.java)
    }
}

class StockSerializer : Serializer<Stock> {
    override fun serialize(topic: String?, data: Stock?): ByteArray? {
        return if (null == data) data else jsonMapper.writeValueAsBytes(data)
    }
}

class StockSerde : Serde<Stock> {
    override fun serializer(): Serializer<Stock> {
        return StockSerializer()
    }

    override fun deserializer(): Deserializer<Stock> {
        return StockDeserializer()
    }
}


