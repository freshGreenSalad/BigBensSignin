package com.example.bigbenssignin.common.data.room

import androidx.room.*
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.TimeCardEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeSheetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllTimecard(vararg timesheet: TimeCardEntry)

    @Query("SELECT * FROM TimeCardEntry")
    fun getAllTimecard(): Flow<List<TimeCardEntry>>

    @Query("SELECT * FROM TimeCardEntry WHERE party_id LIKE :personId"
            )
    fun getOneTimecard(personId: Int): TimeCardEntry

    @Update
    fun updateTimecard (vararg: TimeCardEntry)

    @Delete
    fun deleteTimecard (plant: TimeCardEntry)
}