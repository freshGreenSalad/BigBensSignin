package com.example.bigbenssignin.common.data.time

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

const val chosenTimeRounding = 15

class AppTimeFormats {

    fun getTimeRoundedToQuarterHour(): ZonedDateTime {
        val time = getGmtTime()
        val minuteMod = time.minute % chosenTimeRounding
        return if (minuteMod < 8) {
            time.minusMinutes(minuteMod.toLong())
        } else {
            time.plusMinutes((15-minuteMod).toLong())
        }
    }

    fun toZonedTime(date:String):ZonedDateTime{
        return ZonedDateTime.parse(date, DateTimeFormatter.ISO_INSTANT.withZone( ZoneId.of("GMT+12")))
    }

    fun toIsoFormat(time:ZonedDateTime):String = DateTimeFormatter.ISO_INSTANT.format(time)



    fun getGmtTime():ZonedDateTime {
        return ZonedDateTime.ofInstant(
            Instant.ofEpochMilli(System.currentTimeMillis()),
            ZoneId.of("GMT+12")
        )
    }
}