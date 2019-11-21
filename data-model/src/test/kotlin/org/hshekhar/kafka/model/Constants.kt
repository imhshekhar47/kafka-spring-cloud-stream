package org.hshekhar.kafka.model

import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*

internal val START_DATE_STRING = "2019-01-01T00:00:00.000+0000"

internal val START_DATE_INSTANT = LocalDate
        .ofYearDay(2019, 1)
        .atStartOfDay(ZoneId.of("UTC"))
        .truncatedTo(ChronoUnit.DAYS)
        .toInstant()

internal val START_DATE = Date(START_DATE_INSTANT.toEpochMilli())