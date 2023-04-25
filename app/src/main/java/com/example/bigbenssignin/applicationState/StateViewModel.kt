package com.example.bigbenssignin.applicationState

import androidx.lifecycle.ViewModel
import com.example.bigbenssignin.internetConnection.NetworkConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StateViewModel @Inject constructor(
    networkConnectivityObserver: NetworkConnectivityObserver
): ViewModel() {
    val networkState = networkConnectivityObserver.observe()
}



