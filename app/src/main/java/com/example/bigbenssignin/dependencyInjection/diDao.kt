package com.example.bigbenssignin.dependencyInjection

import com.example.bigbenssignin.common.data.room.BensAppDatabase
import com.example.bigbenssignin.common.data.room.PeopleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun provideWateringDao(database: BensAppDatabase): PeopleDao = database.peopleDao()

}