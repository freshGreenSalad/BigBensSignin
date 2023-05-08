package com.example.bigbenssignin.common.data

import com.example.bigbenssignin.common.domain.models.ApiKeys
import com.example.bigbenssignin.common.domain.models.HttpRequestConstants
import com.example.bigbenssignin.common.domain.models.RequestForRefreshToken
import com.example.bigbenssignin.common.domain.models.ReturnFromRequestForToken
import com.example.bigbenssignin.features.loginToProcoreFeature.domain.models.RequestForToken
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.TimeCardEntryNoKey
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class BasicHttpReqests @Inject constructor(
    private val client: HttpClient,
    private val dataStoreImp: DataStoreFunctions,
) {

    suspend fun sendTimeSheetHttp(timeCardEntry: TimeCardEntryNoKey) {
        client.post(HttpRequestConstants.getTimeCardEntriesUri(dataStoreImp.getProject())) {
            bearerAuth(dataStoreImp.getToken())
            setBody(timeCardEntry)
            contentType(ContentType.Application.Json)
        }
    }

    suspend fun getProjectPeopleHttp(): HttpResponse {
        return client.get(HttpRequestConstants.getProjectPeopleUri(dataStoreImp.getProject())) {
            bearerAuth(dataStoreImp.getToken())
            contentType(ContentType.Application.Json)
        }
    }

    suspend fun httpRequestForToken(authorisationCode: String): ReturnFromRequestForToken{
        return client.post(HttpRequestConstants.tokenRequest) {
            contentType(ContentType.Application.Json)
            setBody(buildRequestForToken(authorisationCode))
        }.body()
    }

    suspend fun httpGetCompaniesList(): HttpResponse {
        return client.get(HttpRequestConstants.companyRequest) {
            bearerAuth(dataStoreImp.getToken())
            contentType(ContentType.Application.Json)
        }
    }

    suspend fun httpGetProjectsList(): HttpResponse {
        return client.get(HttpRequestConstants.getCompanyUri(dataStoreImp.getCompany())) {
            bearerAuth(dataStoreImp.getToken())
            contentType(ContentType.Application.Json)
        }
    }

    suspend fun tokenTimeoutCallBack(procoreApiFunction: suspend () -> String): String {
        dataStoreImp.addTokenToDataStore(httpPostToken(buildRefreshTokenRequest()))
        return procoreApiFunction()
    }

    private suspend fun httpPostToken(request: RequestForRefreshToken): ReturnFromRequestForToken{
        return client.post(HttpRequestConstants.tokenRequest){
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(request))
        }.body()
    }

    private suspend fun buildRefreshTokenRequest():RequestForRefreshToken {
        return RequestForRefreshToken(
            client_id = ApiKeys().clientId,
            client_secret = ApiKeys().clientSecret,
            grant_type = HttpRequestConstants.tokenRequestType_requestToken,
            redirect_uri = HttpRequestConstants.returnUri,
            refresh_token = dataStoreImp.getRefreshToken()
        )
    }

    private fun buildRequestForToken(authorisationCode: String):String {
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

}