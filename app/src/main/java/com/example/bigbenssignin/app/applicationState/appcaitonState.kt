package com.example.bigbenssignin.app.applicationState

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
import com.example.bigbenssignin.app.internetConnection.ConnectivityObserver
import com.example.bigbenssignin.common.presentaiton.Navigation

@Composable
fun ApplicationState(
    viewModel: StateViewModel = hiltViewModel()
) {
    val networkState = viewModel.networkState.collectAsState(initial = ConnectivityObserver.Status.Unavailable)

    Crossfade(targetState = networkState.value, animationSpec = tween(1000)) { networkStateAnim ->
        when (networkStateAnim){
            ConnectivityObserver.Status.Available -> {
                Navigation()
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
