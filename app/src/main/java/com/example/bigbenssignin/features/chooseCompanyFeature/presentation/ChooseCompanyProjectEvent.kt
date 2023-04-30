package com.example.bigbenssignin.features.chooseCompanyFeature.presentation

sealed interface ChooseCompanyProjectEvent {
    data class ChooseProject(val project: String): ChooseCompanyProjectEvent
    data class GetListOfProjects(val company: String): ChooseCompanyProjectEvent
    object GetCompanies: ChooseCompanyProjectEvent
}
