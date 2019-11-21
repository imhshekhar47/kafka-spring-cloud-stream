package org.hshekhar.kafka.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import kotlin.test.Test

internal class TransactionSerdeTest {

    companion object {
        private var serde = TransactionSerde()
    }

    private fun getTestItem(): Pair<String, Transaction> {
        return Pair(
                """{}""",
                Transaction(userId = "test", stockId = "TEST", units = 1, timestamp = START_DATE)
        )
    }

    @Test
    fun `should serialize instance`() {
        val bytes = serde.serializer().serialize(null, getTestItem().second)

        assertNotNull(bytes, "Bytes should not be null")
    }

    @Test
    fun `should deserialize to instance`() {
        val bytes = serde.serializer().serialize(null, getTestItem().second)
        val instance = serde.deserializer().deserialize(null, bytes)

        assertNotNull(instance, "instance should not be null")
        assertEquals(getTestItem().second.timestamp, instance.timestamp, "Property timestamp should match")
    }
}