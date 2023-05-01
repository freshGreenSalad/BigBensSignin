package com.example.bigbenssignin.features.signinSignoutFeature.domain.models

import android.util.Log
import com.example.bigbenssignin.dependencyInjection.IoDispatcher
import com.example.bigbenssignin.features.signinSignoutFeature.data.signInSignOutRepositoryImp
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class useCaseWorkersNotYetSignedin @Inject constructor(
    private val signInSignOutRepositoryImp: signInSignOutRepositoryImp,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    operator fun invoke():Flow<List<People>> {
        return runBlocking(
            CoroutineName("Sample CN") + SupervisorJob() + dispatcher
        ) {
                val listWorkers = signInSignOutRepositoryImp.getListofWorkers().data ?: emptyList<People>()
                val signedinWorkerFlow = signInSignOutRepositoryImp.getListOfSignedInUsers().map { signedinPeople ->

                    val listdeidentifiedperson = mutableListOf<PersonWithoutId>()
                        for (person in signedinPeople){
                        listdeidentifiedperson.add(convertWorkerTothesame(person))
                    }
                        val newlist = mutableListOf<People>()
                        for (person in listWorkers) {
                            if (convertWorkerTothesame(person) in listdeidentifiedperson) {} else {newlist.add(person)}
                        }
                        newlist
                    }
            signedinWorkerFlow
        }
    }
}

fun convertWorkerTothesame(person:People):PersonWithoutId{
    return PersonWithoutId(
        contact = person.contact,
        contact_id = person.contact_id,
        employee_id = person.employee_id,
        first_name = person.first_name,
        id = person.id,
        is_employee = person.is_employee,
        last_name = person.last_name,
        name = person.name,
        origin_id = person.origin_id,
        user_id = person.user_id,
        user_uuid = person.user_uuid,
        work_classification_id = person.work_classification_id,
    )
}