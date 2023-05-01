package com.example.bigbenssignin.common.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.People

@Database(
    entities = [People::class],
    version = 1,
)

abstract class BensAppDatabase: RoomDatabase() {
    abstract fun peopleDao(): PeopleDao
}