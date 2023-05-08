package com.example.bigbenssignin.common.presentaiton

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.bigbenssignin.common.data.dataStore.ProfileKeyIdentifiers
import com.example.bigbenssignin.features.chooseCompanyFeature.presentation.chooseCompanyProject
import com.example.bigbenssignin.features.loginToProcoreFeature.presentation.loginScreen
import com.example.bigbenssignin.features.signinSignoutFeature.presentation.signinSignoutScreen

@Composable
fun Navigation(loggedInProfileKeyIdentifiers: ProfileKeyIdentifiers) {
    val navController = rememberNavController()

    val destination = chooseDestinationBasedOnInformationAboutAccount(loggedInProfileKeyIdentifiers)

    NavHost(
        navController = navController,
        startDestination = destination
    ) {
        loginScreen(navController)
        chooseCompanyProject(navController)
        signinSignoutScreen(navController)
    }
}

private fun chooseDestinationBasedOnInformationAboutAccount(loggedInProfileKeyIdentifiers: ProfileKeyIdentifiers):String{
    return when {
        loggedInProfileKeyIdentifiers.token.isEmpty() -> {
            NavigationDestinations.loginRoute
        }
        loggedInProfileKeyIdentifiers.company.isEmpty() -> {
            NavigationDestinations.SelectCompany
        }
        loggedInProfileKeyIdentifiers.project.isEmpty() -> {
            NavigationDestinations.SelectCompany
        }
        else -> {
            NavigationDestinations.LoginLogout
        }
    }
}
