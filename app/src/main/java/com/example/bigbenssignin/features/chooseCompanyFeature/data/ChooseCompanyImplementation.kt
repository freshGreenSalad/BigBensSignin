package com.example.bigbenssignin.features.chooseCompanyFeature.data

import android.util.Log
import androidx.datastore.core.DataStore
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.ChooseCompanyRepositoryInterface
import com.example.bigbenssignin.common.Domain.models.SuccessState
import com.example.bigbenssignin.common.data.dataStore.LoggedInProfileKeyIdentifiers
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.models.Companies
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
    val datastore : DataStore<LoggedInProfileKeyIdentifiers>
): ChooseCompanyRepositoryInterface {
    override suspend fun getListOfCompanies(): SuccessState<List<Companies>>{
        Log.d("", "in the get list of companies function")

        //try {
        val token = datastore.data.map { it.token }.first()
        Log.d("", "datastore")
        val response = requestTokenFromClient(client, token)
        Log.d("", "ktor")
        val jsonResponse = Json.decodeFromString<List<Companies>>(response.body())
        Log.d("", "json")
        Log.d("", jsonResponse.toString())
        return SuccessState.Success(Json.decodeFromString<List<Companies>>(response.body()))
        /*}catch (e:Exception){
            Log.d("", e.stackTraceToString())
            Log.d("", "failed to get a list of companies from the ProcoreApi")
            SuccessState.Failure("failed to get a list of companies from the ProcoreApi")
        }*/
    }

    override suspend fun getListOfProjects(): SuccessState<String> {
        TODO("Not yet implemented")
    }

    private suspend fun requestTokenFromClient(client: HttpClient, token:String): HttpResponse =
        client.get("https://sandbox.procore.com/rest/v1.0/companies"){
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }
}