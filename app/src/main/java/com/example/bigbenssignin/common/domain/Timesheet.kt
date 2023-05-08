package com.example.bigbenssignin.common.domain

import android.util.Log
import com.example.bigbenssignin.common.data.time.AppTimeFormats
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.People
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.TimeCardEntry
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.TimeCardEntryNoKey
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.timecardNoKey
import java.time.LocalDate
import javax.inject.Inject

class TimesheetFunctions @Inject constructor(){

    fun generateTimeSheet(timesheet: TimeCardEntry):SuccessState<TimeCardEntryNoKey> {
        // throw an error if bad time and catch the specific error
        // val isToSoonToMakeTimeSheet = CheckIfToEarlyToMakeTimeSheet

        val timeSheetNoKey = timecardNoKey(timesheet)
        val loginGmtTime = AppTimeFormats().convertIsoTimeToZonedTime(timeSheetNoKey.time_in)
        val currentGmtTime = AppTimeFormats().getGmtTime()

        val roundedTime = AppTimeFormats().roundTimetoquarterHour(currentGmtTime)
        Log.d("roundedTime", roundedTime.toString())

        return SuccessState.Success(timeSheetNoKey.copy(
            time_out = AppTimeFormats().isoFormat(roundedTime),
            date = LocalDate.now().toString()
        ))
    }

    fun generateTimeSheet(person: People): TimeCardEntry{
        return TimeCardEntry(
            hours = "7.0",
            lunch_time = "60",
            party_id = person.id,
            time_in = AppTimeFormats().getIsoTime(),
            time_out = "",
            date =  "",
            description = "time sheet from bens signin app",
        )
    }
}