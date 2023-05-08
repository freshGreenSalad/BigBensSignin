package com.example.bigbenssignin.features.chooseCompanyFeature.data

import com.example.bigbenssignin.common.data.BasicHttpReqests
import com.example.bigbenssignin.common.data.DataStoreFunctions
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.ChooseCompanyRepositoryInterface
import com.example.bigbenssignin.common.domain.SuccessState
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.models.Companies
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.models.Project
import io.ktor.client.call.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ChooseCompanyImplementation @Inject constructor(
    private val basicHttpRequests: BasicHttpReqests,
    private val dataStoreImp: DataStoreFunctions
): ChooseCompanyRepositoryInterface {
    override suspend fun getListOfCompanies(): SuccessState<List<Companies>> {
        val httpResponse = basicHttpRequests.httpGetCompaniesList()
        return when(httpResponse.status.value){
            200 ->{
                val listCompanies = Json.decodeFromString<List<Companies>>(httpResponse.body())
                SuccessState.Success(listCompanies)
            }
            401 ->{
                val response = basicHttpRequests.tokenTimeoutCallBack(
                    procoreApiFunction = { basicHttpRequests.httpGetCompaniesList().body() }
                )
                val listCompanies = Json.decodeFromString<List<Companies>>(response)
                SuccessState.Success(listCompanies)
            }
            403 ->{SuccessState.Failure("Failed to get list of companies from Procore")}
            else ->{SuccessState.Failure("Failed to get list of companies from Procore else")}
        }
    }

    override suspend fun getListOfProjects(company:String): SuccessState<List<Project>> {
        dataStoreImp.addCompanyToDataStore(company)
        val httpResponse = basicHttpRequests.httpGetProjectsList()
        return when (httpResponse.status.value){
                200 -> {
                    SuccessState.Success(Json.decodeFromString<List<Project>>(httpResponse.body()))
                }
                401 -> {
                    val retry = basicHttpRequests.tokenTimeoutCallBack(
                        procoreApiFunction = { basicHttpRequests.httpGetProjectsList().body() }
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
}