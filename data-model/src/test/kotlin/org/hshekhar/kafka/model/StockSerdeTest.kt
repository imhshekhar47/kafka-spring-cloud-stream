package org.hshekhar.kafka.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class StockSerdeTest {
    companion object {
        private val serde = StockSerde()
    }

    private fun getTestItem(): Pair<String, Stock> {
        return Pair(
                """{"name":"TEST","price":0.0,"timestamp":"$START_DATE_STRING"}""",
                Stock(name = "TEST", price = 0.0, timestamp = START_DATE)
        )
    }

    @Test
    fun `should serialize instance`() {
        val bytes = serde.serializer().serialize(null, getTestItem().second)

        assertNotNull(bytes, "Bytes should not be null")
        assertEquals(getTestItem().first, String(bytes), "String form should match")
    }

    @Test
    fun `should deserialize to instance`() {
        val bytes = serde.serializer().serialize(null, getTestItem().second)
        val instance = serde.deserializer().deserialize(null, bytes)

        assertNotNull(instance, "Instance should not be null")
        assertEquals(getTestItem().second.timestamp, instance.timestamp, "Property timestamp should match")

    }
}