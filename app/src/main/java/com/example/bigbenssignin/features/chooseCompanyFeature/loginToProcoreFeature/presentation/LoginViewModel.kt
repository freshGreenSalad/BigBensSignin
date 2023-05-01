package com.example.bigbenssignin.features.chooseCompanyFeature.loginToProcoreFeature.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bigbenssignin.common.domain.SuccessState
import com.example.bigbenssignin.dependencyInjection.IoDispatcher
import com.example.bigbenssignin.features.chooseCompanyFeature.loginToProcoreFeature.data.SigninRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
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

        private val _successState =  Channel<SuccessState<Unit>>()
        val successState = _successState.receiveAsFlow()

        private val _channel = Channel<String>()
        val loginFailFlow = _channel.receiveAsFlow()

        fun onEvent(event: OnEventLogin){
                when (event){
                        is OnEventLogin.GetToken -> {
                                scope.launch(dispatcher){
                                        val result = signinRepository.tradeAuthorisationCodeForTokenWithProcore(
                                                authorisationCode.value
                                        )
                                        when (result){
                                                is SuccessState.Success<String> ->{
                                                        _successState.send( SuccessState.Success())
                                                }
                                                is SuccessState.Failure<String> ->{
                                                        _successState.send( SuccessState.Failure())
                                                        _channel.send("Your token did not work, it may have timed out")
                                                }
                                        }
                                }
                        }
                        is OnEventLogin.UpdateAuthorisationCode -> {
                                authorisationCode.value = event.AuthorisationCode
                        }
                }
        }
}