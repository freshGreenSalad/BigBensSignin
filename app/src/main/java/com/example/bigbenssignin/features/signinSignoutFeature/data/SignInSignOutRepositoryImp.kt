package com.example.bigbenssignin.features.signinSignoutFeature.data

import android.util.Log
import androidx.datastore.core.DataStore
import com.example.bigbenssignin.common.data.CommonHttpClientFunctionsImp
import com.example.bigbenssignin.common.data.time.AppTimeFormats
import com.example.bigbenssignin.common.data.dataStore.LoggedInProfileKeyIdentifiers
import com.example.bigbenssignin.common.data.room.PeopleDao
import com.example.bigbenssignin.common.data.room.TimeSheetDao
import com.example.bigbenssignin.common.domain.SuccessState
import com.example.bigbenssignin.common.domain.models.HttpRequestConstants
import com.example.bigbenssignin.features.signinSignoutFeature.domain.SignInSignoutRepository
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.time.*
import javax.inject.Inject

class SignInSignOutRepositoryImp @Inject constructor(
    private val client: HttpClient,
    private val datastore :DataStore<LoggedInProfileKeyIdentifiers>,
    private val commonHttpClientFunctionsImp: CommonHttpClientFunctionsImp,
    private val peopleDao: PeopleDao,
    private val timeSheetDao: TimeSheetDao
): SignInSignoutRepository {
    override suspend fun getListOfWorkers(): SuccessState<List<People>> {
        val httpResponse = getProjectPeopleHttp()
        return when(httpResponse.status.value){
            200 ->{
                SuccessState.Success(
                    Json.decodeFromString<List<People>>(httpResponse.body())
                )
            }
            401 ->{
                SuccessState.Success(
                    Json.decodeFromString(
                        commonHttpClientFunctionsImp.tokenTimeoutCallBack {getProjectPeopleHttp().body() }
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

    override suspend fun addPersonToRoom(person: People) {
        peopleDao.insertAll(person)
    }

    override fun getListOfSignedInUsers(): Flow<List<People>> {
        return peopleDao.getall()
    }

    override suspend fun addTimesheetToRoom(person: People) {
        val timesheet = TimeCardEntry(
            hours = "7.0",
            lunch_time = "60",
            party_id = person.id?:0,
            time_in = AppTimeFormats().getIsoTime(),
            time_out = "",
            date =  "",
            description = "time sheet from bens signin app",
        )
        timeSheetDao.insertAlltimecard(timesheet)
    }

    override suspend fun signUserOut(signoutPerson: People):SuccessState<TimeCardEntryNoKey> {
        return try {
            val timesheet = timeSheetDao.getonetimecard(personId = signoutPerson.id!!)
            val generatedTimeSheet = generateTimeSheet(timesheet)
            sendTimeSheetHttp(generatedTimeSheet.data!!)
            timeSheetDao.deleteTimecard(timesheet)
            peopleDao.deletePeople(signoutPerson)
            return generatedTimeSheet
        } catch (e:Exception){
            Log.d("",e.toString())
            SuccessState.Failure("failed to sign user out")
        }
    }




    private fun generateTimeSheet(timesheet: TimeCardEntry):SuccessState<TimeCardEntryNoKey> {
        val timeSheetNoKey = timecardNoKey(timesheet)
        val loginGmtTime = AppTimeFormats().convertIsoTimeToZonedTime(timeSheetNoKey.time_in)
        val currentGmtTime = AppTimeFormats().getGmtTime()

        val roundedTime = AppTimeFormats().roundTimetoquarterHour(currentGmtTime)
        Log.d("roundedTime", roundedTime.toString())

        return SuccessState.Success(timeSheetNoKey.copy(
            time_out = AppTimeFormats().isoFormat(roundedTime),
            date = LocalDate.now().toString()
        ))
    }

    private suspend fun sendTimeSheetHttp(timeCardEntry: TimeCardEntryNoKey) {
        val project = datastore.data.map { it.project }.first()
        val token = datastore.data.map { it.token }.first()
       client.post(HttpRequestConstants.getTimeCardEntriesUri(project)) {
            bearerAuth(token)
            setBody(timeCardEntry)
            contentType(ContentType.Application.Json)
        }
    }

    private suspend fun getProjectPeopleHttp():HttpResponse {
        val project = datastore.data.map { it.project }.first()
        val token = datastore.data.map { it.token }.first()
        return client.get(HttpRequestConstants.getProjectPeopleUri(project)) {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }
    }
}