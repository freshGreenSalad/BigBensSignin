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

/*
"timecard_entry": {
    "hours": "8.0",
    "lunch_time": "60",
    "party_id": 42,
    "time_in": "2020-10-14T07:00:00Z",
    "time_out": "2018-10-14T16:00:00Z",
    "billable": true,
    "date": "2020-10-14",
    "datetime": "2020-05-19T12:00:00Z",
    "description": "This is a description.",
    "timecard_time_type_id": 1,
    "timesheet_id": 41823,
    "cost_code_id": 12345,
    "sub_job_id": 3483483,
    "location_id": 161072,
    "login_information_id": 2341,
    "origin_id": 23423,
    "origin_data": "{'example':'related data'}",
    "line_item_type_id": 42
}*/
