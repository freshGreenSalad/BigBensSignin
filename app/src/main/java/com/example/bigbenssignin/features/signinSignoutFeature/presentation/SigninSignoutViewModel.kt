package com.example.bigbenssignin.features.signinSignoutFeature.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bigbenssignin.dependencyInjection.IoDispatcher
import com.example.bigbenssignin.features.signinSignoutFeature.data.signInSignOutRepositoryImp
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.useCaseWorkersNotYetSignedin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SigninSignoutViewModel @Inject constructor(
    private val signInSignOutRepositoryImp: signInSignOutRepositoryImp,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val getWorkersNotSignedIn : useCaseWorkersNotYetSignedin
):ViewModel() {
    val scope = viewModelScope

    val listOfPeople = getWorkersNotSignedIn().stateIn(
        initialValue = emptyList(),
        scope = viewModelScope, //TODO("change to dispatcher")
        started = WhileSubscribed(5000)
    )

    val signedInPeopleFlow = signInSignOutRepositoryImp.getListOfSignedInUsers().stateIn(
            initialValue = emptyList(),
            scope = viewModelScope, //TODO("change to dispatcher")
            started = WhileSubscribed(5000)
        )

    fun signInSignoutEvent(event: SigninSignoutEvent){
        when(event){
            is SigninSignoutEvent.AddPersonToRoom -> {
                scope.launch(dispatcher) {
                    signInSignOutRepositoryImp.addPersonToRoom(event.person)
                }
            }
            is SigninSignoutEvent.AddTimesheetToRoom -> {
                scope.launch(dispatcher) {
                    signInSignOutRepositoryImp.addTimesheetToRoom(event.person)
                }
            }
            is SigninSignoutEvent.Logout -> {
                scope.launch(dispatcher) {
                    signInSignOutRepositoryImp.signuserOut(event.person)
                }
            }
        }
    }
}