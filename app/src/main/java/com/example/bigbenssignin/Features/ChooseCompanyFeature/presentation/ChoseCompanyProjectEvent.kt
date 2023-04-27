package com.example.bigbenssignin.Features.ChooseCompanyFeature.presentation

sealed interface ChoseCompanyProjectEvent {
    data class choseCompany(val company: String): ChoseCompanyProjectEvent
    data class choseProject(val project: String): ChoseCompanyProjectEvent
}
