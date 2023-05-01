package com.example.bigbenssignin.features.signinSignoutFeature.domain.models

import android.app.Person
import com.example.bigbenssignin.common.domain.SuccessState

interface signInSignoutRepository {
    suspend fun getListofWorkers(): SuccessState<List<People>>

    suspend fun addPersonToRoom(person: Person)

}