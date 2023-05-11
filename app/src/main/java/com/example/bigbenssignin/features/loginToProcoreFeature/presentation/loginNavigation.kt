package com.example.bigbenssignin.features.loginToProcoreFeature.presentation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bigbenssignin.common.presentaiton.NavigationDestinations
import com.example.bigbenssignin.features.loginToProcoreFeature.presentation.viewmodel.LoginViewModel

fun NavGraphBuilder.loginScreen(navController: NavController){
    composable(NavigationDestinations.loginRoute) {
        val viewModel = hiltViewModel<LoginViewModel>()
        LoginToProCore(
            authorisationCode = viewModel.authorisationCode,
            onEventFunction = viewModel::onEvent ,
            navigateToNextScreen =  {navController.navigate(NavigationDestinations.LoggedInSubGraph){
                popUpTo(NavigationDestinations.loginRoute){inclusive = true}
            } },
            viewModel.eventChannel
        )
    }
}

