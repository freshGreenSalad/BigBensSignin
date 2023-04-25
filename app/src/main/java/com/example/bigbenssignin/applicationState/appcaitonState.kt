package com.example.bigbenssignin.applicationState

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bigbenssignin.internetConnection.ConnectivityObserver

@Composable
fun ApplictionState(
    viewModel: StateViewModel = hiltViewModel()
) {
   val networkstate = viewModel.networkState.collectAsState(initial = ConnectivityObserver.Status.Unavailable)
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(text = "Network status: ${networkstate.value}")
    }
}
