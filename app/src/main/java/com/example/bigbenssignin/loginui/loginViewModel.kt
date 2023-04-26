package com.example.bigbenssignin.loginui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bigbenssignin.SuccessState
import com.example.bigbenssignin.di.IoDispatcher
import com.example.bigbenssignin.sharedUi.signinRepo.signinRepo
import com.example.bigbenssignin.tokenRefreshToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class loginViewModel @Inject constructor(
        val signinrepo: signinRepo,
        @IoDispatcher private val dispatcher: CoroutineDispatcher,
):ViewModel() {
        val token =  mutableStateOf("")
        val authorisationCode = mutableStateOf("")

        private val _channel = Channel<String>()
        val shared = _channel.receiveAsFlow()

        val scope = viewModelScope

        fun onEvent(event: onEvent){
                when (event){
                        is onEvent.gettokin -> {
                                scope.launch(dispatcher){
                                        val result = signinrepo.getTokenFromAuthorisationCode(
                                                 authorisationCode.value
                                        )
                                        if (result == SuccessState.Success<String>()){
                                                token.value = result.data.orEmpty()
                                        }
                                        else {
                                                Log.d("in the else block", "in the else block")
                                                _channel.send("Your token did not work, it may have timed out")

                                        }
                                }
                        }
                        is onEvent.updateAuthorisationCode -> {
                                authorisationCode.value = event.AuthorisationCode
                        }
                }
        }
}