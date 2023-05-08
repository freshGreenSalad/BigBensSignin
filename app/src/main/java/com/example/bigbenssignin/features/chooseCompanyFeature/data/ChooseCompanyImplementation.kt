package com.example.bigbenssignin.features.chooseCompanyFeature.data

import androidx.datastore.core.DataStore
import com.example.bigbenssignin.common.data.CommonHttpClientFunctionsImp
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.ChooseCompanyRepositoryInterface
import com.example.bigbenssignin.common.domain.SuccessState
import com.example.bigbenssignin.common.data.dataStore.LoggedInProfileKeyIdentifiers
import com.example.bigbenssignin.common.domain.models.HttpRequestConstants
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.models.Companies
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.models.Project
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ChooseCompanyImplementation @Inject constructor(
    private val client: HttpClient,
    val datastore : DataStore<LoggedInProfileKeyIdentifiers>,
    private val commonHttpClientFunctionsImp: CommonHttpClientFunctionsImp
): ChooseCompanyRepositoryInterface {
    override suspend fun getListOfCompanies(): SuccessState<List<Companies>> {
        val httpResponse = httpGetCompaniesList(client)

        return when(httpResponse.status.value){
            200 ->{
                val listCompanies = Json.decodeFromString<List<Companies>>(httpResponse.body())
                SuccessState.Success(listCompanies)
            }
            401 ->{
                val response = commonHttpClientFunctionsImp.tokenTimeoutCallBack(
                    procoreApiFunction = { httpGetCompaniesList(client).body() }
                )
                val listCompanies = Json.decodeFromString<List<Companies>>(response)
                SuccessState.Success(listCompanies)
            }
            403 ->{SuccessState.Failure("Failed to get list of companies from Procore 403")}
            else ->{SuccessState.Failure("Failed to get list of companies from Procore else")}
        }
    }

    override suspend fun getListOfProjects(company:String): SuccessState<List<Project>> {
        commonHttpClientFunctionsImp.addCompanyToDataStore(company)
        val httpResponse = httpGetProjectsList(client, company)

        return when (httpResponse.status.value){
                200 -> {
                    SuccessState.Success(Json.decodeFromString<List<Project>>(httpResponse.body()))
                }
                401 -> {
                    val retry = commonHttpClientFunctionsImp.tokenTimeoutCallBack(
                        procoreApiFunction = { httpGetProjectsList(client, company).body() }
                    )
                    val jsonClass = Json.decodeFromString<List<Project>>(retry)
                    SuccessState.Success(jsonClass)
                }
                403 -> {
                    SuccessState.Failure("Failed to get list of Projects from Procore")
                }
            else -> {
                SuccessState.Failure("Failed to get list of Projects from Procore")
            }
        }
    }

    private suspend fun httpGetCompaniesList(client: HttpClient): HttpResponse {
        val token = datastore.data.map { it.token }.first()
        return client.get(HttpRequestConstants.companyRequest) {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }
    }

    private suspend fun httpGetProjectsList(client: HttpClient, company:String): HttpResponse {
        val token = datastore.data.map { it.token }.first()
        return client.get(HttpRequestConstants.getCompanyUri(company)) {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }
    }
}