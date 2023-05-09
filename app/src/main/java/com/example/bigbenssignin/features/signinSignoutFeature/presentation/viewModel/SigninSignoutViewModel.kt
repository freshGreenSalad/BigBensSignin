package com.example.bigbenssignin.features.signinSignoutFeature.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bigbenssignin.common.domain.SuccessState
import com.example.bigbenssignin.dependencyInjection.IoDispatcher
import com.example.bigbenssignin.features.signinSignoutFeature.data.SignInSignOutRepositoryImp
import com.example.bigbenssignin.features.signinSignoutFeature.domain.useCases.UseCaseGetWorkersNotSignedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SigninSignoutViewModel @Inject constructor(
    private val signInSignOutRepositoryImp: SignInSignOutRepositoryImp,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    getWorkersNotSignedIn : UseCaseGetWorkersNotSignedIn
):ViewModel() {
    private val scope = viewModelScope

    private val _eventChannel =  Channel<SuccessState<Unit>>()
    val eventChannel = _eventChannel.receiveAsFlow()

    val listOfPeople = getWorkersNotSignedIn().stateIn(
        initialValue = emptyList(),
        scope = viewModelScope,
        started = WhileSubscribed(5000)
    )

    val signedInPeopleFlow = signInSignOutRepositoryImp.getListOfSignedInUsers().stateIn(
            initialValue = emptyList(),
            scope = viewModelScope,
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
                    val successSignout = signInSignOutRepositoryImp.signUserOut(event.person)
                   when (successSignout){
                        is SuccessState.Failure -> {
                            _eventChannel.send(SuccessState.Failure(successSignout.error ?: "error Signing out user"))
                        }
                        is SuccessState.Success -> {
                            _eventChannel.send(SuccessState.Failure(successSignout.data))
                        }
                    }
                }
            }
        }
    }
}