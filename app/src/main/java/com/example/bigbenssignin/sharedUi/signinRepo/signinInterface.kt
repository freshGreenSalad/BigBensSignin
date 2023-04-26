package com.example.bigbenssignin.sharedUi.signinRepo

import com.example.bigbenssignin.SuccessState

interface signinInterface {
    suspend fun getTokenFromAuthorisationCode(authorisationCode:String): SuccessState<String>
}