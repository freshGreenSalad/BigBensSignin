package com.example.bigbenssignin.common.data.room

import androidx.room.*
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.People
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.TimeCardEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeSheetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlltimecard(vararg timesheet: TimeCardEntry)

    @Query("SELECT * FROM TimeCardEntry")
    fun getalltimecard(): Flow<List<TimeCardEntry>>


    @Query("SELECT * FROM TimeCardEntry WHERE party_id LIKE :personId"
            )
    fun getonetimecard(personId: Int): TimeCardEntry

    @Update
    fun updateTimecard (vararg: TimeCardEntry)

    @Delete
    fun deleteTimecard (plant: TimeCardEntry)
}