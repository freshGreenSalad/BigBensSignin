package com.example.bigbenssignin.dependencyInjection

import com.example.bigbenssignin.common.data.room.BensAppDatabase
import com.example.bigbenssignin.common.data.room.PeopleDao
import com.example.bigbenssignin.common.data.room.TimeSheetDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun providePeopleDao(database: BensAppDatabase): PeopleDao = database.peopleDao()

    @Provides
    fun provideTimesheetDao(database: BensAppDatabase): TimeSheetDao = database.timesheetDao()

}