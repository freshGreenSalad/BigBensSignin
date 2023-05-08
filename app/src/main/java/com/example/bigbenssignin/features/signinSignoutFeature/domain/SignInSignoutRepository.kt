package com.example.bigbenssignin.features.signinSignoutFeature.domain

import com.example.bigbenssignin.common.domain.SuccessState
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.People
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.TimeCardEntryNoKey
import kotlinx.coroutines.flow.Flow

interface SignInSignoutRepository {
    suspend fun getListOfWorkers(): SuccessState<List<People>>

    suspend fun addPersonToRoom(person: People)

    fun getListOfSignedInUsers(): Flow<List<People>>

    suspend fun addTimesheetToRoom(person: People)

    suspend fun signUserOut(person: People):SuccessState<TimeCardEntryNoKey>

}