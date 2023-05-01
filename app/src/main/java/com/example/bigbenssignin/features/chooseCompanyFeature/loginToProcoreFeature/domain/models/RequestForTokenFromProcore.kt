package com.example.bigbenssignin.features.chooseCompanyFeature.loginToProcoreFeature.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class RequestForTokenFromProcore(
    val client_id: String,
    val client_secret: String,
    val code: String,
    val grant_type: String,
    val redirect_uri: String
)