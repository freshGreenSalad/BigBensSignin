package com.example.bigbenssignin.common.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.People
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.ContactConverter
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.TimeCardEntry

@Database(
    entities = [People::class, TimeCardEntry::class],
    version = 1,
)

@TypeConverters(ContactConverter::class, )
abstract class BensAppDatabase: RoomDatabase() {
    abstract fun peopleDao(): PeopleDao
    abstract fun timesheetDao(): TimeSheetDao
}