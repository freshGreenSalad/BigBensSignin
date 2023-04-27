package com.example.bigbenssignin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.bigbenssignin.Common.Presentaiton.navigationDestenations
import com.example.bigbenssignin.ChooseCompanyFeature.ChooseCompanyProject
import com.example.bigbenssignin.features.loginToProcoreFeature.presentation.LoginScreen

@Composable
fun navigaiton() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = navigationDestenations.loginRoute) {
        LoginScreen(navController)
        ChooseCompanyProject(navController)
    }
}
