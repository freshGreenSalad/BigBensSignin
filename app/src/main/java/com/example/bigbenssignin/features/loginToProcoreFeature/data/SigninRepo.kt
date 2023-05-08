package com.example.bigbenssignin.features.loginToProcoreFeature.data

import com.example.bigbenssignin.common.data.BasicHttpReqests
import com.example.bigbenssignin.common.domain.SuccessState
import com.example.bigbenssignin.common.domain.models.ApiKeys
import com.example.bigbenssignin.common.data.DataStoreFunctions
import com.example.bigbenssignin.features.loginToProcoreFeature.domain.SigninInterface
import com.example.bigbenssignin.features.loginToProcoreFeature.domain.models.RequestForToken
import com.example.bigbenssignin.common.domain.models.HttpRequestConstants
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SigninRepository @Inject constructor(
    private val basicHttpRequests: BasicHttpReqests,
    private val dataStoreImp: DataStoreFunctions
): SigninInterface {
    override suspend fun getTokenFromProcore(authorisationCode: String): SuccessState<String> {
        return try {
            val token = basicHttpRequests.httpRequestForToken(authorisationCode)
            dataStoreImp.addTokenToDataStore(token)
            SuccessState.Success()
        } catch (e: Exception) {
            SuccessState.Failure("failed to get token with the authorisation code from Procore")
        }
    }
}



