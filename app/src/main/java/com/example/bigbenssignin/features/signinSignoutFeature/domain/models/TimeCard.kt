package com.example.bigbenssignin.features.signinSignoutFeature.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class timecard_entry(
    val billable: Boolean,
    val cost_code_id: Int,
    val date: String,
    val datetime: String,
    val description: String,
    val hours: String,
    val line_item_type_id: Int,
    val location_id: Int,
    val login_information_id: Int,
    val lunch_time: String,
    val origin_data: String,
    val origin_id: Int,
    val party_id: Int,
    val sub_job_id: Int,
    val time_in: String,
    val time_out: String,
    val timecard_time_type_id: Int,
    val timesheet_id: Int
)
