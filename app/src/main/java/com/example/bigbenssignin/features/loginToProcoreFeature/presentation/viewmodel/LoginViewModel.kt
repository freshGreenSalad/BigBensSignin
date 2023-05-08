package com.example.bigbenssignin.features.loginToProcoreFeature.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bigbenssignin.common.domain.SuccessState
import com.example.bigbenssignin.dependencyInjection.IoDispatcher
import com.example.bigbenssignin.features.loginToProcoreFeature.data.SigninRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signinRepository: SigninRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
):ViewModel() {
        private val scope = viewModelScope

        val authorisationCode = mutableStateOf("")

        private val _eventChannel =  Channel<SuccessState<Unit>>()
        val eventChannel = _eventChannel.receiveAsFlow()

        fun onEvent(event: LoginEvents){
                when (event){
                        is LoginEvents.GetToken -> {
                                getToken()
                        }
                        is LoginEvents.UpdateAuthorisationCode -> {
                                authorisationCode.value = event.AuthorisationCode
                        }
                }
        }
        private fun getToken(){
                scope.launch(dispatcher){
                        val result = signinRepository.getTokenFromProcore(
                                authorisationCode.value
                        )
                        when (result){
                                is SuccessState.Success<String> ->{
                                        _eventChannel.send( SuccessState.Success())
                                }
                                is SuccessState.Failure<String> ->{
                                        _eventChannel.send( SuccessState.Failure("Your token did not work, it may have timed out"))
                                }
                        }
                }
        }
}

