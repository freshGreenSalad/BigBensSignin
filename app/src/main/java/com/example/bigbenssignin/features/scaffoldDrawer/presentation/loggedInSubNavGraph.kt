package com.example.bigbenssignin.features.scaffoldDrawer.presentation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.bigbenssignin.common.data.dataStore.ProfileKeyIdentifiers
import com.example.bigbenssignin.common.presentaiton.NavigationDestinations
import com.example.bigbenssignin.features.chooseCompanyFeature.presentation.chooseCompanyProject
import com.example.bigbenssignin.features.signinSignoutFeature.presentation.signinSignoutScreen

@Composable
fun LoggedInSubNavGraph(navController: NavHostController, loggedInProfileKeyIdentifiers: ProfileKeyIdentifiers, snackbarState: SnackbarHostState) {
    val destination = chooseDestinationBasedOnInformationAboutAccount(loggedInProfileKeyIdentifiers)
    NavHost(
        navController = navController,
        route = NavigationDestinations.LoggedInSubGraph,
        startDestination = destination
    ) {
        chooseCompanyProject(navController)
        signinSignoutScreen(navController,snackbarState)
    }
}

private fun chooseDestinationBasedOnInformationAboutAccount(loggedInProfileKeyIdentifiers: ProfileKeyIdentifiers):String{
    return if(loggedInProfileKeyIdentifiers.company.isEmpty()){
        NavigationDestinations.SelectCompany
    }else{NavigationDestinations.SigninSignout}
}