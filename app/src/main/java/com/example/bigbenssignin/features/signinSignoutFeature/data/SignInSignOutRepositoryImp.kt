package com.example.bigbenssignin.features.signinSignoutFeature.data

import android.app.Person
import android.util.Log
import androidx.datastore.core.DataStore
import com.example.bigbenssignin.common.data.CommonHttpClientFunctionsImp
import com.example.bigbenssignin.common.data.dataStore.LoggedInProfileKeyIdentifiers
import com.example.bigbenssignin.common.domain.SuccessState
import com.example.bigbenssignin.common.domain.models.HttpRequestConstants
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.People
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.signInSignoutRepository
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

class signInSignOutRepositoryImp @Inject constructor(
    private val client: HttpClient,
    private val datastore :DataStore<LoggedInProfileKeyIdentifiers>,
    private val commonHttpClientFunctionsImp: CommonHttpClientFunctionsImp
): signInSignoutRepository {
    override suspend fun getListofWorkers(): SuccessState<List<People>> {
        val project = datastore.data.map { it.project }.first()
        val token = datastore.data.map { it.token }.first()
        Log.d("", project)

        val httpResponse = getProjectPeople(project, token)

        val response = when(httpResponse.status.value){
                200 ->{
                    SuccessState.Success(
                    Json.decodeFromString<List<People>>(httpResponse.body())
                )
            }
            401 ->{
                SuccessState.Success(
                    Json.decodeFromString(
                commonHttpClientFunctionsImp.tokenTimeoutCallBack { getProjectPeople(project, token).body() }
                    )
                )
            }
            403 ->{
                SuccessState.Failure()
            }
            else -> {
                SuccessState.Failure()
            }
        }
        Log.d("response", response.data.toString())
        return response
    }

    override suspend fun addPersonToRoom(person: Person) {
        TODO("Not yet implemented")
    }

    private suspend fun getProjectPeople(project: String, token:String):HttpResponse {
        val uri = HttpRequestConstants.procoreBaseUri + "/rest/v1.0/projects/${project}/people"
        val httpresponse = client.get(uri) {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }
        return httpresponse
    }
}