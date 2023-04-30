package com.example.bigbenssignin.common.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class ReturnFromRequestForToken(
    val access_token: String,
    val created_at: Int,
    val expires_in: Int,
    val refresh_token: String,
    val token_type: String
)