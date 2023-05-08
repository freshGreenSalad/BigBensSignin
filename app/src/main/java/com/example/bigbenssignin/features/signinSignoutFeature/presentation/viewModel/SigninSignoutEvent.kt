package com.example.bigbenssignin.features.signinSignoutFeature.presentation.viewModel

import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.People

sealed interface SigninSignoutEvent {

    data class AddPersonToRoom(val person: People) : SigninSignoutEvent

    data class AddTimesheetToRoom(val person: People) : SigninSignoutEvent

    data class Logout(val person: People) : SigninSignoutEvent
}