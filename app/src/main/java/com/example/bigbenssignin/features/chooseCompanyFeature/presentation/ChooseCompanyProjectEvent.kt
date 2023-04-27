package com.example.bigbenssignin.features.chooseCompanyFeature.presentation

sealed interface ChooseCompanyProjectEvent {
    data class choseCompany(val company: String): ChooseCompanyProjectEvent
    data class choseProject(val project: String): ChooseCompanyProjectEvent
}
