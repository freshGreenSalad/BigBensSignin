package com.example.bigbenssignin.features.signinSignoutFeature.presentation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bigbenssignin.common.presentaiton.NavigationDestinations

fun NavGraphBuilder.loginLogoutScreen(navController: NavController){
    composable(NavigationDestinations.LoginLogout){
        val viewModel: SigninSignoutViewModel = hiltViewModel()
        SigninSignoutScreen(
            listPeople = viewModel.listOfPeople,
            addToRoom = viewModel::signInSignoutEvent,
            signedinWorkerList = viewModel.signedInPeopleFlow)
    }
}