package com.example.bigbenssignin.features.loginToProcoreFeature.data

import com.example.bigbenssignin.common.domain.SuccessState
import com.example.bigbenssignin.common.domain.models.ApiKeys
import com.example.bigbenssignin.common.data.CommonHttpClientFunctionsImp
import com.example.bigbenssignin.features.loginToProcoreFeature.domain.SigninInterface
import com.example.bigbenssignin.features.loginToProcoreFeature.domain.models.RequestForToken
import com.example.bigbenssignin.common.domain.models.ReturnFromRequestForToken
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
    override suspend fun getTokenFromProcore(authorisationCode: String): SuccessState<String> {
        return try {
            val token = httpRequestForToken(client, buildJsonRequestForToken(authorisationCode))
            commonHttpClientFunctionsImp.addTokenToDataStore(token)
            SuccessState.Success(token.access_token)
        } catch (e: Exception) {
            SuccessState.Failure<String>("failed to get token with the authorisation code from Procore")
        }
    }

    private fun buildJsonRequestForToken(authorisationCode: String):String {
        return Json.encodeToString(
            RequestForToken(
                client_id = ApiKeys().clientId,
                client_secret = ApiKeys().clientSecret,
                code = authorisationCode,
                grant_type = HttpRequestConstants.tokenRequestType_authorization_code,
                redirect_uri = HttpRequestConstants.returnUri
            )
        )
    }

    private suspend fun httpRequestForToken(client: HttpClient,jsonQuery: String
    ): ReturnFromRequestForToken = client.post(HttpRequestConstants.tokenRequest) {
            contentType(ContentType.Application.Json)
            setBody(jsonQuery)
        }.body()
}



