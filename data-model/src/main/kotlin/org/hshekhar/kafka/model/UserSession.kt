package org.hshekhar.kafka.model

import org.apache.kafka.common.serialization.Deserializer
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serializer

data class UserSession(val userId: String, val state: String)

class UserSessionSerializer: Serializer<UserSession> {
    override fun serialize(topic: String?, data: UserSession?): ByteArray? {
        return if (data == null) null else jsonMapper.writeValueAsBytes(data)
    }
}

class UserSessionDeserializer: Deserializer<UserSession> {
    override fun deserialize(topic: String?, data: ByteArray?): UserSession? {
        return if (data == null) null else jsonMapper.readValue(data, UserSession::class.java)
    }
}

class UserSessionSerde: Serde<UserSession> {
    override fun serializer(): Serializer<UserSession> {
        return UserSessionSerializer()
    }

    override fun deserializer(): Deserializer<UserSession> {
        return UserSessionDeserializer()
    }
}