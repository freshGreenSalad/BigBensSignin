package com.example.bigbenssignin.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.emptyPreferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.bigbenssignin.sharedPreferances.DataStoreImplementaion
import com.example.bigbenssignin.sharedPreferances.DatastoreInterface
import dagger.Provides

@Module
@InstallIn(SingletonComponent::class)
object DISharedPrefs {
    private const val USER_PREFERENCES = "user_preferences"

    @Singleton
    @Provides
    fun provideDataStore(dataStore: DataStoreImplementaion): DatastoreInterface = dataStore

    @Binds
    @Singleton
    fun providesharedPrefs(@ApplicationContext context: Context): DataStore<Preferences> = PreferenceDataStoreFactory.create(
        corruptionHandler = ReplaceFileCorruptionHandler(
            produceNewData = { emptyPreferences() }
        ),
        migrations = listOf(SharedPreferencesMigration(context, USER_PREFERENCES)),
        scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
        produceFile = { context.preferencesDataStoreFile(USER_PREFERENCES) }
    )
}