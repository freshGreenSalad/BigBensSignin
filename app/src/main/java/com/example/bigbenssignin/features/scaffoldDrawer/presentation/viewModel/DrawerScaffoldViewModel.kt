package com.example.bigbenssignin.features.scaffoldDrawer.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bigbenssignin.common.data.DataStoreFunctions
import com.example.bigbenssignin.common.data.dataStore.ProfileKeyIdentifiers
import com.example.bigbenssignin.common.domain.SuccessState
import com.example.bigbenssignin.dependencyInjection.IoDispatcher
import com.example.bigbenssignin.features.scaffoldDrawer.data.ScaffoldDrawerRepoImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawerScaffoldViewModel @Inject constructor(
    private val dataStoreImp: DataStoreFunctions,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val scaffoldDrawerRepoImp: ScaffoldDrawerRepoImp
):ViewModel() {
    private val scope = viewModelScope

    private val _keyLoginData = MutableStateFlow(ProfileKeyIdentifiers())
    val keyLoginData = _keyLoginData

    private val _channel = Channel<SuccessState<Unit>>()
    val channel = _channel.receiveAsFlow()

    init {
        scope.launch(dispatcher) {
            _keyLoginData.value = dataStoreImp.getProfileKeyIdentifiers()
        }
    }

    fun onEvent(event: OnEventScaffoldViewModel){
        when (event){
            OnEventScaffoldViewModel.DeleteEverythingDatastore -> {
                scope.launch(dispatcher) {
                    dataStoreImp.deleteAll()
                }
            }
            OnEventScaffoldViewModel.SubmitAllTimeSheets -> {
                scope.launch(dispatcher) {
                    val successfullysentTimesheets = scaffoldDrawerRepoImp.submitAllTimeSheets()

                    when (successfullysentTimesheets){
                        is SuccessState.Failure -> {
                        _channel.send(SuccessState.Failure(successfullysentTimesheets.error))
                        }
                        is SuccessState.Success -> TODO()
                    }
                }
            }
        }
    }
}