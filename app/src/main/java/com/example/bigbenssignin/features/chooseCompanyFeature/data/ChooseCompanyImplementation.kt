package com.example.bigbenssignin.features.chooseCompanyFeature.data

import android.util.Log
import androidx.datastore.core.DataStore
import com.example.bigbenssignin.common.data.CommonHttpClientFunctionsImp
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.ChooseCompanyRepositoryInterface
import com.example.bigbenssignin.common.domain.SuccessState
import com.example.bigbenssignin.common.data.dataStore.LoggedInProfileKeyIdentifiers
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
        val token = datastore.data.map { it.token }.first()
        val response = try {
            val response = Json.decodeFromString<List<Companies>>(getCompaniesList(client, token))
            SuccessState.Success(response)
        }catch (e:Exception){
            try {
                val response = commonHttpClientFunctionsImp.tokenTimeoutCallBack(
                    procoreApiFunction = { getCompaniesList(client, token) }
                )
                val jsonClass = Json.decodeFromString<List<Companies>>(response)
                SuccessState.Success(jsonClass)
            } catch (e: Exception) {
                SuccessState.Failure("Failed to get list of companies from Procore")
            }
        }
        return response
    }

    override suspend fun getListOfProjects(company:String): SuccessState<List<Project>> { // TODO: the refresh token logic is not working quite right we're still getting an error
        val token = datastore.data.map { it.token }.first()
        val dataStoreCompany = datastore.data.map { it.company }.first()

        Log.d("","getlist of projects repo")
        Log.d("token",token)
        Log.d("company",company)
        Log.d("company",dataStoreCompany)

        val httpResponse = getProjectsList(client, token, company)

        val response = when (httpResponse.status.value){
                200 -> {
                    Log.d("200", httpResponse.body())
                    SuccessState.Success(Json.decodeFromString<List<Project>>(httpResponse.body()))
                }
                401 -> {
                    Log.d("getlistOfProjects", "401")

                    val retry = commonHttpClientFunctionsImp.tokenTimeoutCallBack(
                        procoreApiFunction = { getProjectsList(client, token, company).body() }
                    )
                    val jsonClass = Json.decodeFromString<List<Project>>(retry)
                    SuccessState.Success(jsonClass)
                }
                403 -> {
                    Log.d("getlistOfProjects", "403 error")

                    SuccessState.Failure("Failed to get list of Projects from Procore")
                }
            else -> {
                Log.d("getlistOfProjects", "else error")

                SuccessState.Failure("Failed to get list of Projects from Procore")
            }
        }
        return response
    }

    private suspend fun getCompaniesList(client: HttpClient, token:String): String {
        val response = client.get("https://sandbox.procore.com/rest/v1.0/companies") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }

        return response.body()
    }

    private suspend fun getProjectsList(client: HttpClient, token: String, company:String): HttpResponse {

        val response = client.get("https://sandbox.procore.com/rest/v1.0/companies/${company}/projects") {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }
        return response
    }
}