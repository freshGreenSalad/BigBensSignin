package com.example.bigbenssignin.features.scaffoldDrawer.data

import com.example.bigbenssignin.common.data.BasicHttpReqests
import com.example.bigbenssignin.common.data.room.PeopleDao
import com.example.bigbenssignin.common.data.room.TimeSheetDao
import com.example.bigbenssignin.common.domain.SuccessState
import com.example.bigbenssignin.common.domain.TimesheetFunctions
import com.example.bigbenssignin.features.scaffoldDrawer.domain.ScaffoldDrawerRepo
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ScaffoldDrawerRepoImp @Inject constructor(
    private val timeSheetDao: TimeSheetDao,
    private val basicHttpRequests: BasicHttpReqests,
    private val peopleDao: PeopleDao,
    private val timesheetFunctions: TimesheetFunctions
): ScaffoldDrawerRepo {
    override suspend fun submitAllTimeSheets(): SuccessState<Unit> {
        return try {
            val list = timeSheetDao.getAllTimecard().first()

            if(list.isEmpty()){
                return SuccessState.Failure("No timesheets to send!")
            }

            val shortTimeSheets = mutableListOf<String>()
            list.forEach { timeCardEntry ->
                val person = peopleDao.getPersonById(timeCardEntry.party_id)

                if(timesheetFunctions.timeSheetToShort(timeCardEntry)){
                    if (!person.name.isNullOrEmpty()) {
                        shortTimeSheets.add(person.name)
                    }
                    timeSheetDao.deleteTimecard(timeCardEntry)
                    peopleDao.deletePeople(person)
                    return@forEach
                }

                basicHttpRequests.sendTimeSheetHttp(timesheetFunctions.generateTimeSheet(timeCardEntry))
                timeSheetDao.deleteTimecard(timeCardEntry)
                peopleDao.deletePeople(person)

            }
            SuccessState.Failure("Successfully time sheets. However $shortTimeSheets timesheets where under 15 minutes, so they were not sent")
        } catch (e:Exception){
            SuccessState.Failure("failed to send TimeSheets")
        }
    }
}