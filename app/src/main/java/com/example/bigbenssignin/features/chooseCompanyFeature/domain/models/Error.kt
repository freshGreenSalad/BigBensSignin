package com.example.bigbenssignin.features.chooseCompanyFeature.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class ErrorFromProcoreServer(
    val errors: String
)