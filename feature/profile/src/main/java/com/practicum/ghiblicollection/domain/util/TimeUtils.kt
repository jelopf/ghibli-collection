package com.practicum.ghiblicollection.domain.util

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

val TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

fun tryParseTime(time: String?): LocalTime? {
    return try {
        LocalTime.parse(time.orEmpty(), TIME_FORMATTER)
    } catch (e: DateTimeParseException) {
        null
    }
}

fun formatTime(time: LocalTime): String = time.format(TIME_FORMATTER)