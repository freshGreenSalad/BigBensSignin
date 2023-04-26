package com.example.bigbenssignin.sharedPreferances

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DataStoreImplementaion @Inject constructor(
    private val dataStore: DataStore<Preferences>
): DatastoreInterface {
    override suspend fun deleteAccount() {
        dataStore.edit { it.clear() }
    }
    override suspend fun read(key: String): String? {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

    override suspend fun edit(key: String, value:String) {
        dataStore.edit { settings ->
            settings[stringPreferencesKey(name = key)] = value
        }
    }
}