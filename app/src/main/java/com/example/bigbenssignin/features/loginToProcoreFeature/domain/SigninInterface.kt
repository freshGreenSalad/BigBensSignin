package com.example.bigbenssignin.features.loginToProcoreFeature.domain

import com.example.bigbenssignin.SuccessState

interface SigninInterface {
    suspend fun getTokenFromAuthorisationCode(authorisationCode:String): SuccessState<String>
}