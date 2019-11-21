package org.hshekhar.kafka.model

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

internal val jsonMapper = ObjectMapper().apply {
    registerKotlinModule()
    dateFormat = StdDateFormat()
}