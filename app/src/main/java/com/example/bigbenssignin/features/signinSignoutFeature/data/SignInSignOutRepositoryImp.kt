package com.example.bigbenssignin.features.signinSignoutFeature.data

import android.util.Log
import androidx.datastore.core.DataStore
import com.example.bigbenssignin.common.data.CommonHttpClientFunctionsImp
import com.example.bigbenssignin.common.data.dataStore.LoggedInProfileKeyIdentifiers
import com.example.bigbenssignin.common.data.room.PeopleDao
import com.example.bigbenssignin.common.data.room.TimeSheetDao
import com.example.bigbenssignin.common.domain.SuccessState
import com.example.bigbenssignin.common.domain.models.HttpRequestConstants
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
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class signInSignOutRepositoryImp @Inject constructor(
    private val client: HttpClient,
    private val datastore :DataStore<LoggedInProfileKeyIdentifiers>,
    private val commonHttpClientFunctionsImp: CommonHttpClientFunctionsImp,
    private val peopleDao: PeopleDao,
    private val timeSheetDao: TimeSheetDao
): signInSignoutRepository {
    override suspend fun getListofWorkers(): SuccessState<List<People>> {
        val project = datastore.data.map { it.project }.first()
        val token = datastore.data.map { it.token }.first()

        val httpResponse = getProjectPeople(project, token)

        val response = when(httpResponse.status.value){
            200 ->{
                SuccessState.Success(
                    Json.decodeFromString<List<People>>(httpResponse.body())
                )
            }
            401 ->{
                SuccessState.Success(
                    Json.decodeFromString(
                        commonHttpClientFunctionsImp.tokenTimeoutCallBack {newToken -> getProjectPeople(project, token).body() }
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
        Log.d("response", response.data.toString())
        return response
    }

    override suspend fun addPersonToRoom(person: People) {
        peopleDao.insertAll(person)
    }

    override fun getListOfSignedInUsers(): Flow<List<People>> {
        return peopleDao.getall()
    }

    override suspend fun addTimesheetToRoom(person: People) {

        val timein = ZonedDateTime.ofInstant(
            Instant.ofEpochMilli(System.currentTimeMillis()),
            ZoneId.of("GMT+12")
        ).truncatedTo(ChronoUnit.SECONDS)
        val formatedtimeIn = DateTimeFormatter.ISO_INSTANT.format(timein)

        val timesheet = TimeCardEntry(
            hours = "7.0",
            lunch_time = "60",
            party_id = person.id?:0,
            time_in = formatedtimeIn.toString(),
            time_out = "",
            date =  "",
            description = "time sheet from bens signin app",
        )
        timeSheetDao.insertAlltimecard(timesheet)
    }

    override suspend fun signuserOut(person: People) {

        val project = datastore.data.map { it.project }.first()

        val token = datastore.data.map { it.token }.first()

        val timeOut = ZonedDateTime.ofInstant(
            Instant.ofEpochMilli(System.currentTimeMillis()),
            ZoneId.of("GMT+12")
        ).plusHours(1)

        val formatedtimeOut = DateTimeFormatter.ISO_INSTANT.format(timeOut)

        val date = LocalDate.now()

        try {
            val timesheet = timeSheetDao.getonetimecard(personId = person.id!!)
            timeSheetDao.deleteTimecard(timesheet)
            val timeSheetWithoutAutoGenerate = timecardwithoutprimarykey(timesheet)
            val timeSheetWithTimeOutAndDate = timeSheetWithoutAutoGenerate.copy(time_out = formatedtimeOut, date = date.toString())
            sendTimeSheetToProcore(timeSheetWithTimeOutAndDate,  project, token)
            peopleDao.deletePeople(person)
        } catch (e:Exception){
            Log.d("",e.toString())
        }
    }

    private suspend fun sendTimeSheetToProcore(timeCardEntry: TimeCardEntryWithoutAutoGenerate, project: String, token:String) {
        val uri = HttpRequestConstants.getTimeCardEntriesUri(project)
       client.post(uri) {
            bearerAuth(token)
            setBody(timeCardEntry)
            contentType(ContentType.Application.Json)
        }
    }

    private suspend fun getProjectPeople(project: String, token:String):HttpResponse {
        val uri = HttpRequestConstants.getProjectPeopleUri(project)
        val httpResponse = client.get(uri) {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }
        return httpResponse
    }
}