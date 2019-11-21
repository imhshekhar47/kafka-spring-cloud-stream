package org.hshekhar.kafka.model

import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serializer

data class SessionState(val userId: String, val state: String = "inactive")

class SessionStateSerializer : Serializer<SessionState> {
    override fun serialize(topic: String?, data: SessionState?): ByteArray? {
        return if (null == data) null else jsonMapper.writeValueAsBytes(data)
    }
}

class SessionStateDeserializer : Deserializer<SessionState> {
    override fun deserialize(topic: String?, data: ByteArray?): SessionState? {
        return if (null == data) null else jsonMapper.readValue(data, SessionState::class.java)
    }
}

class SessionStateSerde : Serde<SessionState> {
    override fun serializer(): Serializer<SessionState> {
        return SessionStateSerializer()
    }

    override fun deserializer(): Deserializer<SessionState> {
        return SessionStateDeserializer()
    }
}

