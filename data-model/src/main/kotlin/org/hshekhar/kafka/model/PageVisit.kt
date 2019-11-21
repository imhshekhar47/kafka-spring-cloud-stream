package org.hshekhar.kafka.model

import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serializer
import java.util.*

data class PageVisit(val userId: String, val pageId: String, val duration: Long, val timestamp: Date = Date())

class PageVisitSerializer : Serializer<PageVisit> {
    override fun serialize(topic: String?, data: PageVisit?): ByteArray? {
        return if (null == data) null else jsonMapper.writeValueAsBytes(data)
    }
}

class PageVisitDeserializer : Deserializer<PageVisit> {
    override fun deserialize(topic: String?, data: ByteArray?): PageVisit? {
        return if (null == data) null else jsonMapper.readValue(data, PageVisit::class.java)
    }
}

class PageVisitSerde : Serde<PageVisit> {
    override fun serializer(): Serializer<PageVisit> {
        return PageVisitSerializer()
    }

    override fun deserializer(): Deserializer<PageVisit> {
        return PageVisitDeserializer()
    }
}
