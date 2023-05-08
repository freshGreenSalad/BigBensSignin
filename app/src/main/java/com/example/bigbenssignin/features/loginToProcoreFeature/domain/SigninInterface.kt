package com.example.bigbenssignin.features.loginToProcoreFeature.domain

import com.example.bigbenssignin.common.domain.SuccessState

interface SigninInterface {
    suspend fun getTokenFromProcore(authorisationCode:String): SuccessState<String>
}