package com.example.bigbenssignin.features.signinSignoutFeature.data

import android.util.Log
import com.example.bigbenssignin.common.data.BasicHttpReqests
import com.example.bigbenssignin.common.data.room.PeopleDao
import com.example.bigbenssignin.common.data.room.TimeSheetDao
import com.example.bigbenssignin.common.domain.SuccessState
import com.example.bigbenssignin.common.domain.TimesheetFunctions
import com.example.bigbenssignin.features.signinSignoutFeature.domain.SignInSignoutRepository
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.*
import io.ktor.client.call.*
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SignInSignOutRepositoryImp @Inject constructor(
    private val peopleDao: PeopleDao,
    private val timeSheetDao: TimeSheetDao,
    private val basicHttpRequests: BasicHttpReqests,
    private val timesheetFunctions: TimesheetFunctions
): SignInSignoutRepository {
    override suspend fun getListOfWorkers(): SuccessState<List<People>> {
        val httpResponse = basicHttpRequests.getProjectPeopleHttp()
        return when(httpResponse.status.value){
            200 ->{
                SuccessState.Success(
                    Json.decodeFromString<List<People>>(httpResponse.body())
                )
            }
            401 ->{
                SuccessState.Success(
                    Json.decodeFromString(
                        basicHttpRequests.tokenTimeoutCallBack {basicHttpRequests.getProjectPeopleHttp().body() }
                    )
                )
            }
            403 ->{
                SuccessState.Failure()
            }
            else -> {
                SuccessState.Failure()
            }
        }
    }

    override suspend fun signUserOut(person: People):SuccessState<String> {
        val timesheet = timeSheetDao.getOneTimecard(personId = person.id)

        if(timesheetFunctions.timeSheetToShort(timesheet)){
            timeSheetDao.deleteTimecard(timesheet)
            peopleDao.deletePeople(person)
            return SuccessState.Failure("${person.name}'s timesheet was to short, it has not been added to procore")
        }

        basicHttpRequests.sendTimeSheetHttp(timesheetFunctions.generateTimeSheet(timesheet))
        timeSheetDao.deleteTimecard(timesheet)
        peopleDao.deletePeople(person)
        return SuccessState.Success("successfully added ${person.name}'s timesheet to procore")
    }

    override suspend fun addPersonToRoom(person: People) {
        peopleDao.insertAll(person)
    }

    override fun getListOfSignedInUsers(): Flow<List<People>> {
        return peopleDao.getall()
    }

    override suspend fun addTimesheetToRoom(person: People) {
        timeSheetDao.insertAllTimecard(timesheetFunctions.generateTimeSheet(person))
    }
}