package com.example.bigbenssignin.features.signinSignoutFeature.presentation

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bigbenssignin.common.domain.SuccessState
import com.example.bigbenssignin.dependencyInjection.IoDispatcher
import com.example.bigbenssignin.features.signinSignoutFeature.data.signInSignOutRepositoryImp
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.People
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SigninSignoutViewModel @Inject constructor(
    private val signInSignOutRepositoryImp: signInSignOutRepositoryImp,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
):ViewModel() {
    val scope = viewModelScope

    val listOfPeople = mutableStateOf<List<People>>(emptyList())

    init {
        signInSignoutEvent(SigninSignoutEvent.GetListOfPeople)
        Log.d("", "initviewmodel")
    }

    fun signInSignoutEvent(event: SigninSignoutEvent){
        when(event){
            SigninSignoutEvent.GetListOfPeople -> {
                scope.launch(dispatcher) {
                    when(
                    val people = signInSignOutRepositoryImp.getListofWorkers()
                    ){
                        is SuccessState.Failure -> {listOfPeople.value = emptyList()
                        }
                        is SuccessState.Success -> {
                            listOfPeople.value = people.data ?: emptyList()
                        }
                    }
                }

            }
            is SigninSignoutEvent.AddToRoom -> {
                scope.launch(dispatcher) {
                    signInSignOutRepositoryImp.addPersonToRoom(event.person)
                }
            }
        }
    }
}