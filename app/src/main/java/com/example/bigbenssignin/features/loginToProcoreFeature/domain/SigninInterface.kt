package com.example.bigbenssignin.features.loginToProcoreFeature.domain

import com.example.bigbenssignin.SuccessState

interface SigninInterface {
    suspend fun tradeAuthorisationCodeForTokenWithProcore(authorisationCode:String): SuccessState<String>
}