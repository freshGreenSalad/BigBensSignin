package com.example.bigbenssignin.chooseCompany

import androidx.datastore.core.DataStore
import com.example.bigbenssignin.tokenRefreshToken
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class ChoseCompanyImplementation @Inject constructor(
    private val client: HttpClient,
    val datastore : DataStore<tokenRefreshToken>
):choseCompanyInterface {
    override suspend fun getcompanys(): ListOfCompaniesItem {
        return try {
            var token = ""
            datastore.data.collect{
                token = it.token
            }
             client.get("https://sandbox.procore.com/rest/v1.0/companies"){
                bearerAuth(token)
                contentType(ContentType.Application.Json)
            }.body<ListOfCompaniesItem>()
        }catch (e:Exception){
            ListOfCompaniesItem(0,true, "", "", "")
        }
    }

    override suspend fun getprojects(): String {
        TODO("Not yet implemented")
    }
}