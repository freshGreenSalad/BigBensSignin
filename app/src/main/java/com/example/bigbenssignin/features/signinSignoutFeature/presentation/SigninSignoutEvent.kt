package com.example.bigbenssignin.features.signinSignoutFeature.presentation

import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.People

sealed interface SigninSignoutEvent {
    object GetListOfPeople :SigninSignoutEvent

    data class AddToRoom(val person: People) :SigninSignoutEvent
}