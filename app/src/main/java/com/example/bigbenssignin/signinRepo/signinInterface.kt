package com.example.bigbenssignin.signinRepo

import com.example.bigbenssignin.SuccessState

interface signinInterface {
    suspend fun getTokenFromAuthorisationCode(authorisationCode:String): SuccessState<String>
}