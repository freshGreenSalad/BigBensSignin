package com.example.bigbenssignin.chooseCompany

sealed interface ChoseCompanyProjectEvent {
    data class choseCompany(val company: String): ChoseCompanyProjectEvent
    data class choseProject(val project: String): ChoseCompanyProjectEvent
}
