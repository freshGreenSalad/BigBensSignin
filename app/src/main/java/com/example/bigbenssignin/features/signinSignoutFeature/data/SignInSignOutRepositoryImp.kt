package com.example.bigbenssignin.features.signinSignoutFeature.data

import android.app.Person
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
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
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
        Log.d("", project)

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
                        commonHttpClientFunctionsImp.tokenTimeoutCallBack { getProjectPeople(project, token).body() }
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
        )
        val formatedtimeIn = DateTimeFormatter.ISO_INSTANT.format(timein)

        Log.d("formatedTime", formatedtimeIn)

        val date = ""
        val datetime = ""

        val timesheet = TimeCardEntry(
            hours = "8.0",
            lunch_time = "60",
            party_id = person.id?:0,
            time_in = "2023-5-1T07:00:12Z",
            time_out = "2023-5-1T16:00:12Z",
            /*billable = true,
            date = date,
            datetime = datetime,*/
            description = "time sheet from bens signin app",
            /*timecard_time_type_id = 1,
            timesheet_id = 0,
            cost_code_id = 0,
            sub_job_id = 0,
            location_id = 0,
            login_information_id = 0,
            origin_id = 0,
            origin_data = "",
            line_item_type_id = 0,*/
        )

        /*"2023-5-1T07:00:12Z",
        "time_out": "2023-5-1T16:00:12Z"*/
        timeSheetDao.insertAlltimecard(timesheet)
    }

    override suspend fun signuserOut(person: People) {
        val timein = ZonedDateTime.ofInstant(
            Instant.ofEpochMilli(System.currentTimeMillis()),
            ZoneId.of("GMT+12")
        )
        val formatedtimeIn = DateTimeFormatter.ISO_INSTANT.format(timein)
        val project = datastore.data.map { it.project }.first()
        val token = datastore.data.map { it.token }.first()
        try {
            val timesheet = timeSheetDao.getonetimecard(personId = person.id!!)
            Log.d("timesheet", timesheet.toString())
            timeSheetDao.deleteTimecard(timesheet)
            val timesheetwithoutAutoGenerate = timecardwithoutprimarykey(timesheet)
           // timesheetwithoutAutoGenerate.copy(time_out = formatedtimeIn)
            sendtimesheetToProcore(timesheetwithoutAutoGenerate,  project, token)
            peopleDao.deletePeople(person)
        } catch (e:Exception){
            Log.d("",e.toString())
        }
    }

    private suspend fun sendtimesheetToProcore(timeCardEntry: TimeCardEntryWithoutAutoGenerate, project: String, token:String) {
        val uri = HttpRequestConstants.procoreBaseUri + "/rest/v1.0/projects/${project}/timecard_entries"
        try {
            val status = client.post(uri) {
                bearerAuth(token)
                setBody(timeCardEntry)
                contentType(ContentType.Application.Json)
            }
            Log.d("status",status.status.toString())
            Log.d("status",status.call.toString())
            Log.d("status",status.request.toString())

        } catch (e: Exception) {
            Log.d("http",e.toString())
        }

    }

    private suspend fun getProjectPeople(project: String, token:String):HttpResponse {
        Log.d("token", token)
        val uri = HttpRequestConstants.procoreBaseUri + "/rest/v1.0/projects/${project}/people"
        val httpresponse = client.get(uri) {
            bearerAuth(token)
            contentType(ContentType.Application.Json)
        }
        return httpresponse
    }
}