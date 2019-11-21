package org.hshekhar.kafka.model

import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class PageVisitSerdeTest {

    companion object {
        private val serde = PageVisitSerde()
    }

    private fun getTestItem(): Pair<String, PageVisit> {
        val dt = Date(START_DATE_INSTANT.toEpochMilli())
        return Pair(
                """{"userId":"test","pageId":"test","duration":10,"timestamp":"$START_DATE_STRING"}""",
                PageVisit(userId = "test", pageId = "test", duration = 10L, timestamp = dt)
        )
    }

    @Test
    fun `should serialize instance`() {
        val bytes = serde.serializer().serialize(null, getTestItem().second)
        assertNotNull(bytes, "Bytes should not be null")
        assertEquals(getTestItem().first, String(bytes))
    }

    @Test
    fun `should deserialize to instance`() {
        val bytes = serde.serializer().serialize(null, getTestItem().second)
        val instance = serde.deserializer().deserialize(null, bytes)

        assertNotNull(instance, "Deserialized instance should not be null")
        assertEquals(getTestItem().second.timestamp, instance.timestamp, "Property timestamp should match")
    }
}