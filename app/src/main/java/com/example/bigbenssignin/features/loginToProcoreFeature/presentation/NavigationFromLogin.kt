package com.example.bigbenssignin.features.loginToProcoreFeature.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bigbenssignin.common.presentaiton.NavigationDestinations
import com.example.bigbenssignin.common.domain.SuccessState
import com.example.bigbenssignin.features.loginToProcoreFeature.presentation.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.Flow

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

