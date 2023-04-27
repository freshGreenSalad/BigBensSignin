package com.example.bigbenssignin.features.chooseCompanyFeature.data

import android.util.Log
import androidx.datastore.core.DataStore
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.choseCompanyInterface
import com.example.bigbenssignin.ChooseCompanyFeature.ListOfCompaniesItem
import com.example.bigbenssignin.tokenRefreshToken
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class ChooseCompanyImplementation @Inject constructor(
    private val client: HttpClient,
    val datastore : DataStore<tokenRefreshToken>
): choseCompanyInterface {
    override suspend fun getcompanys(): List<ListOfCompaniesItem> {
        return try {
            var token = ""
            datastore.data.collect{
                token = it.token
            }
             val response = client.get("https://sandbox.procore.com/rest/v1.0/companies"){
                bearerAuth(token)
                contentType(ContentType.Application.Json)
            }
            Log.d("", response.body())
            return response.body<List<ListOfCompaniesItem>>()

        }catch (e:Exception){
            emptyList<ListOfCompaniesItem>()
        }
    }

    override suspend fun getprojects(): String {
        TODO("Not yet implemented")
    }
}