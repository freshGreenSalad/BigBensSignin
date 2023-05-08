package com.example.bigbenssignin.common.data

import androidx.datastore.core.DataStore
import com.example.bigbenssignin.common.data.dataStore.ProfileKeyIdentifiers
import com.example.bigbenssignin.common.domain.models.ReturnFromRequestForToken
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreFunctions @Inject constructor(
    private val dataStore : DataStore<ProfileKeyIdentifiers>,
) {
    suspend fun getToken():String = dataStore.data.map { it.token }.first()
    suspend fun getProject():String = dataStore.data.map { it.project }.first()
    suspend fun getCompany():String = dataStore.data.map { it.company }.first()
    suspend fun getRefreshToken():String = dataStore.data.map { it.refreshToken}.first()

    suspend fun addTokenToDataStore(
        token: ReturnFromRequestForToken,
    ) {
        dataStore.updateData { data ->
            data.copy(token = token.access_token, refreshToken = token.refresh_token)
        }
    }

    suspend fun addCompanyToDataStore(
        company:String,
    ){
        dataStore.updateData { data ->
            data.copy(company = company)
        }
    }

    suspend fun addProjectToDataStore(
        project:String,
    ){
        dataStore.updateData { data ->
            data.copy(project = project)
        }
    }
}