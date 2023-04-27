package com.example.bigbenssignin.features.loginToProcoreFeature.data

import androidx.datastore.core.DataStore
import com.example.bigbenssignin.SuccessState
import com.example.bigbenssignin.keys
import com.example.bigbenssignin.features.loginToProcoreFeature.domain.SigninInterface
import com.example.bigbenssignin.features.loginToProcoreFeature.domain.models.RequestForTokenFromProcore
import com.example.bigbenssignin.features.loginToProcoreFeature.domain.models.ReturnFromRequestForToken
import com.example.bigbenssignin.tokenRefreshToken
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SigninRepository @Inject constructor(
    private val client: HttpClient,
    val datastore :DataStore<tokenRefreshToken>
): SigninInterface {
    override suspend fun tradeAuthorisationCodeForTokenWithProcore(authorisationCode: String): SuccessState<String> {
        val jsonQuery = Json.encodeToString(
            RequestForTokenFromProcore(
                client_id = keys().client_id,
                client_secret = keys().client_secret,
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
        datastore: DataStore<tokenRefreshToken>
    ) =
        try {
            val token = httpRequestForTokenWithProcore(client, jsonQuery)
            addTokenToDataStore(token, datastore)
            SuccessState.Success(token.access_token)
        } catch (e: Exception) {
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

    private suspend fun addTokenToDataStore(
        token: ReturnFromRequestForToken,
        datastore: DataStore<tokenRefreshToken>
    ) {
        datastore.updateData { data ->
            data.copy(token = token.access_token, refreshToken = token.refresh_token)
        }
    }
}



