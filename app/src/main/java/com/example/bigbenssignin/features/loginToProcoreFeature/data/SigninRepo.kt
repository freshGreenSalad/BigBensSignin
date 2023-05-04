package com.example.bigbenssignin.features.loginToProcoreFeature.data

import androidx.datastore.core.DataStore
import com.example.bigbenssignin.common.domain.SuccessState
import com.example.bigbenssignin.common.domain.models.ApiKeys
import com.example.bigbenssignin.common.data.CommonHttpClientFunctionsImp
import com.example.bigbenssignin.features.loginToProcoreFeature.domain.SigninInterface
import com.example.bigbenssignin.features.loginToProcoreFeature.domain.models.RequestForTokenFromProcore
import com.example.bigbenssignin.common.domain.models.ReturnFromRequestForToken
import com.example.bigbenssignin.common.data.dataStore.LoggedInProfileKeyIdentifiers
import com.example.bigbenssignin.common.domain.models.HttpRequestConstants
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SigninRepository @Inject constructor(
    private val client: HttpClient,
    private val commonHttpClientFunctionsImp: CommonHttpClientFunctionsImp
): SigninInterface {
    override suspend fun tradeAuthorisationCodeForTokenWithProcore(authorisationCode: String): SuccessState<String> {
        val jsonQuery = Json.encodeToString(
            RequestForTokenFromProcore(
                client_id = ApiKeys().clientId,
                client_secret = ApiKeys().clientSecret,
                code = authorisationCode,
                grant_type = HttpRequestConstants.tokenRequestType_authorization_code,
                redirect_uri = HttpRequestConstants.returnUri
            )
        )
        return tryRetreiveTokenFromProcoreWithAutorisationCode(client, jsonQuery)
    }
    private suspend fun tryRetreiveTokenFromProcoreWithAutorisationCode(
        client: HttpClient,
        jsonQuery: String
    ) = try {
        val token = httpRequestForToken(client, jsonQuery)
        commonHttpClientFunctionsImp.addTokenToDataStore(token)
        SuccessState.Success(token.access_token)
    } catch (e: Exception) {
        SuccessState.Failure<String>("failed to get token with the authorisation code from Procore")
    }

    private suspend fun httpRequestForToken(client: HttpClient,jsonQuery: String
    ): ReturnFromRequestForToken =
        client.post(HttpRequestConstants.tokenRequest) {
            contentType(ContentType.Application.Json)
            setBody(jsonQuery)
        }.body()
}



