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

    override suspend fun signUserOut(person: People):SuccessState<TimeCardEntryNoKey> {
        return try {

            val timesheet = timeSheetDao.getonetimecard(personId = person.id)
            val generatedTimeSheet = timesheetFunctions.generateTimeSheet(timesheet)
            //if fail at this point return

            basicHttpRequests.sendTimeSheetHttp(generatedTimeSheet.data!!) // this should be type cast at this point

            //clean up of signed in user
            timeSheetDao.deleteTimecard(timesheet)
            peopleDao.deletePeople(person)

            return generatedTimeSheet
        } catch (e:Exception){
            Log.d("",e.toString())
            SuccessState.Failure("failed to sign user out")
        }
    }

    override suspend fun addPersonToRoom(person: People) {
        peopleDao.insertAll(person)
    }

    override fun getListOfSignedInUsers(): Flow<List<People>> {
        return peopleDao.getall()
    }

    override suspend fun addTimesheetToRoom(person: People) {
        timeSheetDao.insertAlltimecard(timesheetFunctions.generateTimeSheet(person))
    }
}