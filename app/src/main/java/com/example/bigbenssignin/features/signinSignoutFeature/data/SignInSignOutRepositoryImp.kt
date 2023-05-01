package com.example.bigbenssignin.features.signinSignoutFeature.data

import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.signInSignoutRepository
import javax.inject.Inject

class signInSignOutRepositoryImp @Inject constructor(): signInSignoutRepository {
    override suspend fun getListofWorkers() {
        TODO("Not yet implemented")
    }
}