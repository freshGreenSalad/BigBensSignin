package com.example.bigbenssignin.features.scaffoldDrawer.data

import com.example.bigbenssignin.common.data.BasicHttpReqests
import com.example.bigbenssignin.common.data.room.PeopleDao
import com.example.bigbenssignin.common.data.room.TimeSheetDao
import com.example.bigbenssignin.common.domain.SuccessState
import com.example.bigbenssignin.common.domain.TimesheetFunctions
import com.example.bigbenssignin.features.scaffoldDrawer.domain.ScaffoldDrawerRepo
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.People
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ScaffoldDrawerRepoImp @Inject constructor(
    private val timeSheetDao: TimeSheetDao,
    private val basicHttpReqests: BasicHttpReqests,
    private val peopleDao: PeopleDao,
    private val timesheetFunctions: TimesheetFunctions
): ScaffoldDrawerRepo {
    override suspend fun submitAllTimeSheets() {
        val list = timeSheetDao.getAllTimecard().first()
        list.forEach { timeCardEntry ->
            val person = peopleDao.getPersonById(timeCardEntry.party_id)

            if(timesheetFunctions.timeSheetToShort(timeCardEntry)){
                timeSheetDao.deleteTimecard(timeCardEntry)
                peopleDao.deletePeople(person)
                //return SuccessState.Failure("timesheet to short")
            }

            basicHttpReqests.sendTimeSheetHttp(timesheetFunctions.generateTimeSheet(timeCardEntry))
            timeSheetDao.deleteTimecard(timeCardEntry)
            peopleDao.deletePeople(person)
            //return SuccessState.Success()
        }
    }
}