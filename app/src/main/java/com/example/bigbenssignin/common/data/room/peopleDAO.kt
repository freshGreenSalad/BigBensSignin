package com.example.bigbenssignin.common.data.room

import androidx.room.*
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.People
import kotlinx.coroutines.flow.Flow

@Dao
interface PeopleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg plant: People)

    @Query("SELECT * FROM People")
    fun getall(): Flow<List<People>>

    @Update
    fun updatePeople (vararg: People)

    @Delete
    fun deletePeople (plant: People)
}