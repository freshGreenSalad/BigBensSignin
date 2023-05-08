package com.example.bigbenssignin.features.signinSignoutFeature.domain.UseCases

import com.example.bigbenssignin.dependencyInjection.IoDispatcher
import com.example.bigbenssignin.features.signinSignoutFeature.data.SignInSignOutRepositoryImp
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.People
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UseCaseGetWorkersNotSignedIn @Inject constructor(
    private val signInSignOutRepositoryImp: SignInSignOutRepositoryImp,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    operator fun invoke():Flow<List<People>> {
        return runBlocking(
            CoroutineName("Sample CN") + SupervisorJob() + dispatcher
        ) {
                val listOfWorkersFromProcore = signInSignOutRepositoryImp.getListOfWorkers().data ?: emptyList()
                val listOfWorkersSavedInRoom = signInSignOutRepositoryImp.getListOfSignedInUsers().map { signedInPeople ->
                        val workersNotInRoom = mutableListOf<People>()
                        for (person in listOfWorkersFromProcore) {
                            if (person !in signedInPeople) {workersNotInRoom.add(person)}
                        }
                        workersNotInRoom
                    }
            listOfWorkersSavedInRoom
        }
    }
}