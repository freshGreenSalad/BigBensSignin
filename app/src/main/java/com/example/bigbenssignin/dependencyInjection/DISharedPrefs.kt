package com.example.bigbenssignin.dependencyInjection

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.example.bigbenssignin.common.data.dataStore.TokenSerialiser
import com.example.bigbenssignin.common.data.dataStore.LoggedInProfileKeyIdentifiers
import dagger.Provides

@Module
@InstallIn(SingletonComponent::class)
object DISharedPrefs {
    private const val PROTO_DATASTORE_FILE = "token_refreshToken"

    @Provides
    @Singleton
    fun provideProtoDataStore(@ApplicationContext context: Context): DataStore<LoggedInProfileKeyIdentifiers> {
        return DataStoreFactory.create(
            serializer = TokenSerialiser,
            produceFile = {context.dataStoreFile(PROTO_DATASTORE_FILE)},
            corruptionHandler = null
        )
    }
}