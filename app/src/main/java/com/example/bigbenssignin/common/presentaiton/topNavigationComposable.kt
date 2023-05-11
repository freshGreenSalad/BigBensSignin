package com.example.bigbenssignin.common.presentaiton

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.bigbenssignin.common.data.dataStore.ProfileKeyIdentifiers
import com.example.bigbenssignin.features.loginToProcoreFeature.presentation.loginScreen
import com.example.bigbenssignin.features.scaffoldDrawer.presentation.loggedInSubGraph

@Composable
fun TopNavigationComposable(loggedInProfileKeyIdentifiers: ProfileKeyIdentifiers) {
    val navController = rememberNavController()
    val navigateToLoginOrSubGraph = topGraphDestination(loggedInProfileKeyIdentifiers)

    NavHost(
        navController = navController,
        startDestination = navigateToLoginOrSubGraph
    ) {
        loginScreen(navController)
        loggedInSubGraph(navigateToLoginPage =  {
            navController.navigate(NavigationDestinations.loginRoute){
                popUpTo(NavigationDestinations.LoggedInSubGraph){inclusive = true}
            }
        })
    }
}

private fun topGraphDestination(loggedInProfileKeyIdentifiers: ProfileKeyIdentifiers):String{
    return if(loggedInProfileKeyIdentifiers.token.isEmpty()) {
        NavigationDestinations.loginRoute
    }else {NavigationDestinations.LoggedInSubGraph }
}

