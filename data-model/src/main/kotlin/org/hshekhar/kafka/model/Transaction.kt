package org.hshekhar.kafka.model

import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serializer
import java.util.*

data class Transaction(val userId: String, val stockId: String, val price: Double, val units: Int, val timestamp: Date = Date())

class TransactionSerializer : Serializer<Transaction> {
    override fun serialize(topic: String?, data: Transaction?): ByteArray? {
        return if (null == data) data else jsonMapper.writeValueAsBytes(data)
    }
}

class TransactionDeserializer : Deserializer<Transaction> {
    override fun deserialize(topic: String?, data: ByteArray?): Transaction? {
        return if (null == data) data else jsonMapper.readValue(data, Transaction::class.java)
    }
}

class TransactionSerde : Serde<Transaction> {
    override fun serializer(): Serializer<Transaction> {
        return TransactionSerializer()
    }

    override fun deserializer(): Deserializer<Transaction> {
        return TransactionDeserializer()
    }
}