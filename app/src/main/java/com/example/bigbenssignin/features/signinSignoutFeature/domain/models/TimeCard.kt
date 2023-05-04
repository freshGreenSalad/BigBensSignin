package com.example.bigbenssignin.features.signinSignoutFeature.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class TimeCardEntry(
    @PrimaryKey(autoGenerate = true) val key:Int = 0,
    val description: String,
    val hours: String,
    val lunch_time: String,
    val party_id: Int,
    val time_in: String,
    val time_out: String,
)


@Serializable
data class TimeCardEntryWithoutAutoGenerate(
    val description: String,
    val hours: String,
    val lunch_time: String,
    val party_id: Int,
    val time_in: String,
    val time_out: String,
)
fun timecardwithoutprimarykey(timeCardEntry: TimeCardEntry):TimeCardEntryWithoutAutoGenerate{
    return TimeCardEntryWithoutAutoGenerate(
        description = timeCardEntry.description,
        hours = timeCardEntry.hours,
        lunch_time = timeCardEntry.lunch_time,
        party_id = timeCardEntry.party_id,
        time_in = timeCardEntry.time_in,
        time_out = timeCardEntry.time_out,
    )
}
