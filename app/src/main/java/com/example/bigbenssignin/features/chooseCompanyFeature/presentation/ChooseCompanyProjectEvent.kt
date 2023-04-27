package com.example.bigbenssignin.features.chooseCompanyFeature.presentation

sealed interface ChooseCompanyProjectEvent {
    data class ChooseCompany(val company: String): ChooseCompanyProjectEvent
    data class ChooseProject(val project: String): ChooseCompanyProjectEvent
    object getCompanies: ChooseCompanyProjectEvent
}
