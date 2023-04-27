package com.example.bigbenssignin.Features.LoginToProcoreFeature.Domain

import com.example.bigbenssignin.SuccessState

interface signinInterface {
    suspend fun getTokenFromAuthorisationCode(authorisationCode:String): SuccessState<String>
}