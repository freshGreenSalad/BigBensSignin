package com.example.bigbenssignin.common.presentaiton

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.bigbenssignin.common.data.dataStore.LoggedInProfileKeyIdentifiers
import com.example.bigbenssignin.features.chooseCompanyFeature.presentation.chooseCompanyProject
import com.example.bigbenssignin.features.loginToProcoreFeature.presentation.LoginScreen

@Composable
fun Navigation(loggedInProfileKeyIdentifiers: LoggedInProfileKeyIdentifiers) {
    val navController = rememberNavController()

    val destination = chooseDestinationBasedOnInformationAboutAccount(loggedInProfileKeyIdentifiers)

    NavHost(
        navController = navController,
        startDestination = destination
    ) {
        LoginScreen(navController)
        chooseCompanyProject(navController)
    }
}

private fun chooseDestinationBasedOnInformationAboutAccount(loggedInProfileKeyIdentifiers: LoggedInProfileKeyIdentifiers):String{
    return when {
        loggedInProfileKeyIdentifiers.token.isEmpty() -> {
            NavigationDestinations.loginRoute
        }
        loggedInProfileKeyIdentifiers.company.isEmpty() -> {
            NavigationDestinations.SelectCompany
        }
        else -> {
            ""
        }
    }
}
