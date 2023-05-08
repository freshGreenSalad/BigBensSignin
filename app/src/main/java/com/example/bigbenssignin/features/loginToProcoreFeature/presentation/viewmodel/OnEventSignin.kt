package com.example.bigbenssignin.features.loginToProcoreFeature.presentation.viewmodel


sealed interface LoginEvents{
    object GetToken: LoginEvents
    data class UpdateAuthorisationCode(val AuthorisationCode: String): LoginEvents
}

