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
        val token = datastore.data.map { it.token }.first()
        val response = requestTokenFromClient(client, token)
        Log.d("response from procore", response.status.toString())
        return SuccessState.Success(Json.decodeFromString<List<Companies>>(response.body()))
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