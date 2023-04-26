package com.example.bigbenssignin.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
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
import com.example.bigbenssignin.TokenSerialiser
import com.example.bigbenssignin.sharedPreferances.DataStoreImplementaion
import com.example.bigbenssignin.sharedPreferances.DatastoreInterface
import com.example.bigbenssignin.tokenRefreshToken
import dagger.Provides

@Module
@InstallIn(SingletonComponent::class)
object DISharedPrefs {
    private const val USER_PREFERENCES = "user_preferences"
    private const val PROTO_DATASTORE_FILE = "token_refreshToken"

    @Singleton
    @Provides
    fun provideDataStore(dataStore: DataStoreImplementaion): DatastoreInterface = dataStore

    @Provides
    @Singleton
    fun providesharedPrefs(@ApplicationContext context: Context): DataStore<Preferences> = PreferenceDataStoreFactory.create(
        corruptionHandler = ReplaceFileCorruptionHandler(
            produceNewData = { emptyPreferences() }
        ),
        migrations = listOf(SharedPreferencesMigration(context, USER_PREFERENCES)),
        scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
        produceFile = { context.preferencesDataStoreFile(USER_PREFERENCES) }
    )


    @Provides
    @Singleton
    fun provideportoDataStore(@ApplicationContext context: Context): DataStore<tokenRefreshToken> {
        return DataStoreFactory.create(
            serializer = TokenSerialiser,
            produceFile = {context.dataStoreFile(PROTO_DATASTORE_FILE)},
            corruptionHandler = null
        )
    }
}