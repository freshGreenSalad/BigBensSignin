package com.example.bigbenssignin.features.signinSignoutFeature.domain.models

data class Worker(
    val contact: Contact,
    val contact_id: Int,
    val employee_id: Any,
    val first_name: String,
    val id: Int,
    val is_employee: Boolean,
    val last_name: String,
    val name: String,
    val origin_id: Any,
    val user_id: Int,
    val user_uuid: String,
    val work_classification_id: Any
)

data class Contact(
    val is_active: Boolean
)