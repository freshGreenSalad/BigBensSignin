package com.example.bigbenssignin.features.signinSignoutFeature.domain.models

interface signInSignoutRepository {
    suspend fun getListofWorkers()

}