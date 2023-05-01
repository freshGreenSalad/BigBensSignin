package com.example.bigbenssignin.features.chooseCompanyFeature.loginToProcoreFeature.presentation


sealed interface OnEventLogin{
    object GetToken: OnEventLogin
    data class UpdateAuthorisationCode(val AuthorisationCode: String): OnEventLogin
}

