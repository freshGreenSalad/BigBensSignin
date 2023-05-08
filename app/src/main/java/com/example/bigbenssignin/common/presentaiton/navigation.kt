package com.example.bigbenssignin.common.presentaiton

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.bigbenssignin.common.data.dataStore.ProfileKeyIdentifiers
import com.example.bigbenssignin.features.loginToProcoreFeature.presentation.loginScreen
import com.example.bigbenssignin.homeSubGraph

@Composable
fun Navigation(loggedInProfileKeyIdentifiers: ProfileKeyIdentifiers) {
    val navController = rememberNavController()

    val destination = topGraphDesination(loggedInProfileKeyIdentifiers)

    NavHost(
        navController = navController,
        startDestination = destination
    ) {
        loginScreen(navController)
        homeSubGraph()
    }
}



private fun topGraphDesination(loggedInProfileKeyIdentifiers: ProfileKeyIdentifiers):String{
    return when {
        loggedInProfileKeyIdentifiers.token.isEmpty() -> {
            NavigationDestinations.loginRoute
        }
        loggedInProfileKeyIdentifiers.company.isEmpty() -> { "Graph.HOME"}
        loggedInProfileKeyIdentifiers.project.isEmpty() -> { "Graph.HOME"}
        else -> {"Graph.HOME" }
    }
}

