package com.example.bigbenssignin.features.chooseCompanyFeature.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Project(
    val address: Address,
    val id: Int,
    val name: String,
    val open_items: List<OpenItem>,
    val stage_name: String?,
    val status_name: String?,
    val type_name: String?
)

@Serializable
data class Address(
    val city: String?,
    val country_code: String?,
    val state_code: String?,
    val street: String?,
    val zip: String? // TODO: zip is an int but its being sent as a null via an empty string
)

@Serializable
data class OpenItem(
    val details: String,
    val host: String,
    val id: Int,
    val url: String
)