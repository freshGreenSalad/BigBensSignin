package com.example.bigbenssignin.common.presentaiton

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.bigbenssignin.common.data.dataStore.ProfileKeyIdentifiers
import com.example.bigbenssignin.features.loginToProcoreFeature.presentation.loginScreen
import com.example.bigbenssignin.features.scaffoldDrawer.presentation.homeSubGraph

@Composable
fun Navigation(loggedInProfileKeyIdentifiers: ProfileKeyIdentifiers) {
    val navController = rememberNavController()

    val destination = topGraphDestination(loggedInProfileKeyIdentifiers)

    NavHost(
        navController = navController,
        startDestination = destination
    ) {
        loginScreen(navController)
        homeSubGraph { navController.navigate(NavigationDestinations.loginRoute) }
    }
}



private fun topGraphDestination(loggedInProfileKeyIdentifiers: ProfileKeyIdentifiers):String{
    return when {
        loggedInProfileKeyIdentifiers.token.isEmpty() -> {
            NavigationDestinations.loginRoute
        }
        loggedInProfileKeyIdentifiers.company.isEmpty() -> { NavigationDestinations.LoggedInSubGraph}
        loggedInProfileKeyIdentifiers.project.isEmpty() -> { NavigationDestinations.LoggedInSubGraph}
        else -> {NavigationDestinations.LoggedInSubGraph }
    }
}

