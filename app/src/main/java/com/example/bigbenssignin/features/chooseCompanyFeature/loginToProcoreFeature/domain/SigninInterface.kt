package com.example.bigbenssignin.features.chooseCompanyFeature.loginToProcoreFeature.domain

import com.example.bigbenssignin.common.domain.SuccessState

interface SigninInterface {
    suspend fun tradeAuthorisationCodeForTokenWithProcore(authorisationCode:String): SuccessState<String>
}