package com.example.bigbenssignin.features.signinSignoutFeature.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class People(
    val contact: Contact?,
    val contact_id: Int?,
    val employee_id: String?,
    val first_name: String?,
    val id: Int?,
    val is_employee: Boolean?,
    val last_name: String?,
    val name: String?,
    val origin_id: Int?,
    val user_id: Int?,
    val user_uuid: String?,
    val work_classification_id: Int?
)
@Serializable
data class Contact(
    val is_active: Boolean? = true
)