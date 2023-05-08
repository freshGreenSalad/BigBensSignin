package com.example.bigbenssignin.common.domain

import com.example.bigbenssignin.common.data.time.AppTimeFormats
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.People
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.TimeCardEntry
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.TimeCardEntryNoKey
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.timecardNoKey
import java.time.LocalDate
import javax.inject.Inject

class TimesheetFunctions @Inject constructor(){
    fun timeSheetToShort(timesheet: TimeCardEntry):Boolean{
        val currentTime = AppTimeFormats().getTimeRoundedToQuarterHour()
        val signInTime = AppTimeFormats().toZonedTime(timesheet.time_in)
        return currentTime.compareTo(signInTime) < 16
    }

    fun generateTimeSheet(timesheet: TimeCardEntry):TimeCardEntryNoKey {
        val timeSheetNoKey = timecardNoKey(timesheet)
        return timeSheetNoKey.copy(
            time_out = AppTimeFormats().toIsoFormat(AppTimeFormats().getTimeRoundedToQuarterHour()),
            date = LocalDate.now().toString()
        )
    }

    fun generateTimeSheet(person: People): TimeCardEntry{
        return TimeCardEntry(
            hours = "7.0",
            lunch_time = "60",
            party_id = person.id,
            time_in = AppTimeFormats().toIsoFormat(AppTimeFormats().getTimeRoundedToQuarterHour()),
            time_out = "",
            date =  "",
            description = "time sheet from bens signin app",
        )
    }
}