package com.example.bigbenssignin.app.applicationState

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bigbenssignin.app.internetConnection.NetworkConnectivityObserver
import com.example.bigbenssignin.common.data.dataStore.ProfileKeyIdentifiers
import com.example.bigbenssignin.dependencyInjection.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StateViewModel @Inject constructor(
    networkConnectivityObserver: NetworkConnectivityObserver,
    dataStore: DataStore<ProfileKeyIdentifiers>,
    @IoDispatcher dispatcher: CoroutineDispatcher
): ViewModel() {
    val scope = viewModelScope

    private val _tokenAndRefreshToken = MutableStateFlow(ProfileKeyIdentifiers())
    val tokenAndRefreshToken = _tokenAndRefreshToken

    init {
        scope.launch(dispatcher) {
            tokenAndRefreshToken.value = dataStore.data.map { it }.first()
        }
    }

    val networkState = networkConnectivityObserver.observe()
}



