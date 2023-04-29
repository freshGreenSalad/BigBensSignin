package com.example.bigbenssignin.common.Domain.models

data class RequestForRefreshToken(
    val client_id: String,
    val client_secret: String,
    val grant_type: String,
    val redirect_uri: String,
    val refresh_token: String
)