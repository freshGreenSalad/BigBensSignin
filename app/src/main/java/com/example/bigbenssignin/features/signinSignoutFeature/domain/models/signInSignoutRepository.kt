package com.example.bigbenssignin.features.signinSignoutFeature.domain.models

import android.app.Person
import com.example.bigbenssignin.common.domain.SuccessState
import kotlinx.coroutines.flow.Flow

interface signInSignoutRepository {
    suspend fun getListofWorkers(): SuccessState<List<People>>

    suspend fun addPersonToRoom(person: People)

    fun getListOfSignedInUsers(): Flow<List<People>>

    suspend fun addTimesheetToRoom(person: People)

    suspend fun signuserOut(person: People)

}