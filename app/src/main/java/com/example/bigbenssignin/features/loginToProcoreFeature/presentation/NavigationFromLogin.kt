package com.example.bigbenssignin.features.loginToProcoreFeature.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bigbenssignin.common.presentaiton.NavigationDestinations
import com.example.bigbenssignin.common.domain.SuccessState
import kotlinx.coroutines.flow.Flow

fun NavGraphBuilder.loginScreen(navController: NavController){
    composable(NavigationDestinations.loginRoute) {
        val viewModel = hiltViewModel<LoginViewModel>()
        LoginToProCore(
            authorisationCode = viewModel.authorisationCode,
            onEventFunction = viewModel::onEvent ,
            LoginFail = viewModel.loginFailFlow,
            navigateToNextScreen =  {navController.navigate(NavigationDestinations.SelectCompany){
                popUpTo(NavigationDestinations.loginRoute){inclusive = true}
            } },
            viewModel.successState
        )
    }
}

@Composable
fun NavigateToSelectCompanyOnSuccessState(successState: Flow<SuccessState<Unit>>, navigateToNextScreen: ()-> Unit) {
    LaunchedEffect(successState){
        successState.collect{successFlow ->
            when (successFlow){
                is SuccessState.Failure -> {

                }
                is SuccessState.Success -> {
                    navigateToNextScreen()
                }
                else -> {}
            }
        }
    }
}
