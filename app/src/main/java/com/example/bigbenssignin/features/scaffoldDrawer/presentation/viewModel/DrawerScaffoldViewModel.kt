package com.example.bigbenssignin.features.scaffoldDrawer.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bigbenssignin.common.data.DataStoreFunctions
import com.example.bigbenssignin.common.data.dataStore.ProfileKeyIdentifiers
import com.example.bigbenssignin.dependencyInjection.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawerScaffoldViewModel @Inject constructor(
    val dataStoreImp: DataStoreFunctions,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
):ViewModel() {
    val scope = viewModelScope

    private val _keyLoginData = MutableStateFlow(ProfileKeyIdentifiers())
    val keyLoginData = _keyLoginData

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
        }
    }
}