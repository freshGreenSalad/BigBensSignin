package com.example.bigbenssignin.common.data.time

import org.junit.Assert.*

import org.junit.Test

class AppTimeFormatsTest {

    @Test
    fun getTimeRoundedToQuarterHour() {
        val time = AppTimeFormats().getGmtTime()
        val newTime = AppTimeFormats().getTimeRoundedToQuarterHour(time)
        assertNotEquals(time, newTime)
    }

    @Test
    fun toZonedTime() {
    }

    @Test
    fun toIsoFormat() {
    }
}