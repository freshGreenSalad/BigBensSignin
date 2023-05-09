package com.example.bigbenssignin.features.scaffoldDrawer.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.bigbenssignin.common.data.dataStore.ProfileKeyIdentifiers
import com.example.bigbenssignin.common.presentaiton.NavigationDestinations
import com.example.bigbenssignin.features.chooseCompanyFeature.presentation.chooseCompanyProject
import com.example.bigbenssignin.features.signinSignoutFeature.presentation.signinSignoutScreen

@Composable
fun HomeNavGraph(navController: NavHostController, loggedInProfileKeyIdentifiers: ProfileKeyIdentifiers) {
    val destination = chooseDestinationBasedOnInformationAboutAccount(loggedInProfileKeyIdentifiers)
    NavHost(
        navController = navController,
        route = "Graph.HOME",
        startDestination = destination
    ) {
        chooseCompanyProject(navController)
        signinSignoutScreen(navController)
    }
}

private fun chooseDestinationBasedOnInformationAboutAccount(loggedInProfileKeyIdentifiers: ProfileKeyIdentifiers):String{
    return when {
        loggedInProfileKeyIdentifiers.token.isEmpty() -> {
            NavigationDestinations.SelectCompany
        }
        loggedInProfileKeyIdentifiers.company.isEmpty() -> {
            NavigationDestinations.SelectCompany
        }
        loggedInProfileKeyIdentifiers.project.isEmpty() -> {
            NavigationDestinations.SelectCompany
        }
        else -> {
            NavigationDestinations.SigninSignout
        }
    }
}