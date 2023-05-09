package com.example.bigbenssignin.features.signinSignoutFeature.presentation

import androidx.compose.material3.SnackbarHostState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bigbenssignin.common.presentaiton.NavigationDestinations
import com.example.bigbenssignin.features.signinSignoutFeature.presentation.viewModel.SigninSignoutViewModel

fun NavGraphBuilder.signinSignoutScreen(navController: NavController, snackbarState: SnackbarHostState){
    composable(NavigationDestinations.SigninSignout){
        val viewModel: SigninSignoutViewModel = hiltViewModel()
        SigninSignoutScreen(
            listPeople = viewModel.listOfPeople,
            addToRoom = viewModel::signInSignoutEvent,
            signedInWorkerList = viewModel.signedInPeopleFlow,
            snackbarState,
            viewModel.eventChannel
        )
    }
}