package com.example.bigbenssignin.common.data.time

import android.util.Log
import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class AppTimeFormats {
    fun getIsoTime():String {
        return DateTimeFormatter.ISO_INSTANT.format(
            ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(System.currentTimeMillis()),
                ZoneId.of("GMT+12")
            )
        )
    }

    fun getGmtTime():ZonedDateTime {
        return ZonedDateTime.ofInstant(
                Instant.ofEpochMilli(System.currentTimeMillis()),
                ZoneId.of("GMT+12")
            )
    }

    fun convertIsoTimeToZonedTime(date:String):ZonedDateTime{
        return ZonedDateTime.parse(date, DateTimeFormatter.ISO_INSTANT.withZone( ZoneId.of("GMT+12")))
    }

    fun isoFormat(time:ZonedDateTime):String = DateTimeFormatter.ISO_INSTANT.format(time)

    fun roundTimetoquarterHour(time: ZonedDateTime): ZonedDateTime {
        val newtime = time
        val minuiteMod = time.minute % 15
        if (minuiteMod < 8) {
            newtime.minusMinutes(minuiteMod.toLong())
            Log.d("mod", minuiteMod.toString())
        } else {
            newtime.plusMinutes((15 - minuiteMod).toLong())
        }
        return newtime
    }

    /*fun handletimesheetlessthen15mins(){
        val timeDiffrenceMins = Duration.between( loginGmtTime , currentGmtTime ).toMinutes().toInt()
        Log.d("timeDiffrenceMins", timeDiffrenceMins.toString() )

        if (timeDiffrenceMins < 15){
            //     return //return a fail here
        }
    }*/
}