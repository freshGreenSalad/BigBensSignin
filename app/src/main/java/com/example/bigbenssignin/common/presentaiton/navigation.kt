package com.example.bigbenssignin.common.presentaiton

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.bigbenssignin.features.chooseCompanyFeature.presentation.chooseCompanyProject
import com.example.bigbenssignin.features.loginToProcoreFeature.presentation.LoginScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavigationDestinations.loginRoute) {
        LoginScreen(navController)
        chooseCompanyProject(navController)
    }
}
