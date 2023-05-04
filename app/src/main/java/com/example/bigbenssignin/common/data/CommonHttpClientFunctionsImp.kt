package com.example.bigbenssignin.common.data

import android.util.Log
import androidx.datastore.core.DataStore
import com.example.bigbenssignin.common.domain.CommonHttpClientFunctions
import com.example.bigbenssignin.common.domain.models.ApiKeys
import com.example.bigbenssignin.common.domain.models.HttpRequestConstants
import com.example.bigbenssignin.common.domain.models.RequestForRefreshToken
import com.example.bigbenssignin.common.data.dataStore.LoggedInProfileKeyIdentifiers
import com.example.bigbenssignin.common.domain.models.ReturnFromRequestForToken
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class CommonHttpClientFunctionsImp @Inject constructor(
    private val dataStore: DataStore<LoggedInProfileKeyIdentifiers>,
    private val client: HttpClient
): CommonHttpClientFunctions {

    override suspend fun tokenTimeoutCallBack(procoreApiFunction: suspend (token:String) -> String): String {
        val loggedInProfileKeyIdentifiers = dataStore.data.map { it }.first()
        getTokenFromRefreshToken(loggedInProfileKeyIdentifiers)
        val newToken = dataStore.data.map { it.token }.first()
        return procoreApiFunction(newToken)
    }

    override suspend fun getTokenFromRefreshToken(loggedInProfileKeyIdentifiers: LoggedInProfileKeyIdentifiers) {
        val request = RequestForRefreshToken(
            client_id = ApiKeys().clientId,
            client_secret = ApiKeys().clientSecret,
            grant_type = HttpRequestConstants.tokenRequestType_requestToken,
            redirect_uri = HttpRequestConstants.returnUri,
            refresh_token = loggedInProfileKeyIdentifiers.refreshToken
        )

        val requestForToken = client.post(HttpRequestConstants.tokenRequest){
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(request))
        }.body<ReturnFromRequestForToken>()

        addTokenToDataStore(requestForToken)
    }

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