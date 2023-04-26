package com.example.bigbenssignin.applicationState

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bigbenssignin.internetConnection.ConnectivityObserver
import com.example.bigbenssignin.loginui.Home
import com.example.bigbenssignin.navigation.navigaiton

@Composable
fun ApplictionState(
    viewModel: StateViewModel = hiltViewModel()
) {
    val networkstate = viewModel.networkState.collectAsState(initial = ConnectivityObserver.Status.Unavailable)

    Crossfade(targetState = networkstate.value, animationSpec = tween(1000)) { networkStateAnim ->
        when (networkStateAnim){
            ConnectivityObserver.Status.Available -> {
                navigaiton()
            }
            ConnectivityObserver.Status.Unavailable -> {
                NetworkLoading()
            }
            ConnectivityObserver.Status.Losing -> {
                NetworkLoading()
            }
            ConnectivityObserver.Status.Lost -> {
                NetworkLoading()
            }
        }
    }
}

@Composable
fun NetworkLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment =  Alignment.Center
    ){
        CircularProgressIndicator()
    }
}
