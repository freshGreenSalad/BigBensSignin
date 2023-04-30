package com.example.bigbenssignin.features.loginToProcoreFeature.data

import android.util.Log
import androidx.datastore.core.DataStore
import com.example.bigbenssignin.common.domain.SuccessState
import com.example.bigbenssignin.common.domain.models.ApiKeys
import com.example.bigbenssignin.common.data.CommonHttpClientFunctionsImp
import com.example.bigbenssignin.features.loginToProcoreFeature.domain.SigninInterface
import com.example.bigbenssignin.features.loginToProcoreFeature.domain.models.RequestForTokenFromProcore
import com.example.bigbenssignin.common.domain.models.ReturnFromRequestForToken
import com.example.bigbenssignin.common.data.dataStore.LoggedInProfileKeyIdentifiers
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SigninRepository @Inject constructor(
    private val client: HttpClient,
    private val datastore :DataStore<LoggedInProfileKeyIdentifiers>,
    private val commonHttpClientFunctionsImp: CommonHttpClientFunctionsImp
): SigninInterface {
    override suspend fun tradeAuthorisationCodeForTokenWithProcore(authorisationCode: String): SuccessState<String> {
        val jsonQuery = Json.encodeToString(
            RequestForTokenFromProcore(
                client_id = ApiKeys().clientId,
                client_secret = ApiKeys().clientSecret,
                code = authorisationCode,
                grant_type = "authorization_code",
                redirect_uri = "urn:ietf:wg:oauth:2.0:oob"
            )
        )
        return httpRequestForTokenWithProcore(client, jsonQuery, datastore)
    }
    private suspend fun httpRequestForTokenWithProcore(
        client: HttpClient,
        jsonQuery: String,
        datastore: DataStore<LoggedInProfileKeyIdentifiers>
    ) = try {
        val token = httpRequestForTokenWithProcore(client, jsonQuery)
        commonHttpClientFunctionsImp.addTokenToDataStore(token, datastore)
        SuccessState.Success(token.access_token)
    } catch (e: Exception) {
        Log.d("", e.stackTraceToString())
        SuccessState.Failure("failed to get token with the authorisation code from Procore")
    }

    private suspend fun httpRequestForTokenWithProcore(
        client: HttpClient,
        jsonQuery: String
    ): ReturnFromRequestForToken =
        client.post("https://sandbox.procore.com/oauth/token") {
            contentType(ContentType.Application.Json)
            setBody(jsonQuery)
        }.body()
}



