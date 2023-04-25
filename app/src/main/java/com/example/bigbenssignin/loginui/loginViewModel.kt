package com.example.bigbenssignin.loginui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bigbenssignin.SuccessState
import com.example.bigbenssignin.di.IoDispatcher
import com.example.bigbenssignin.signinRepo.signinRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class loginViewModel @Inject constructor(
        val signinrepo: signinRepo,
        @IoDispatcher private val dispatcher: CoroutineDispatcher
):ViewModel() {

        val authorisationCode = mutableStateOf("")

        val scope = viewModelScope

        fun onEvent(event: onEvent){
                when (event){
                        is onEvent.gettokin -> {
                                scope.launch(dispatcher){
                                        val result = signinrepo.getTokenFromAuthorisationCode(
                                                authorisationCode.value
                                        )
                                        if (result == SuccessState.Success<String>()){
                                              //  onEvent()
                                        }
                                        else {}
                                }
                        }
                        is onEvent.updateAuthorisationCode -> {
                                authorisationCode.value = event.AuthorisationCode
                        }
                }
        }
}