package com.example.bigbenssignin.dependencyInjection

import android.content.Context
import androidx.room.Room
import com.example.bigbenssignin.common.data.room.BensAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): BensAppDatabase =
        Room.databaseBuilder(
            context,
            BensAppDatabase::class.java,
            "bensAppDatabase"
        ).build()
}