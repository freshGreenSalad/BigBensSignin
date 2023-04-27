package com.example.bigbenssignin.features.chooseCompanyFeature.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Companies(
    val id: Int,
    val is_active: Boolean,
    val logo_url: String,
    val name: String,
    val pcn_business_experience: Boolean?
)
