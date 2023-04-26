package com.example.bigbenssignin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.bigbenssignin.chooseCompany.ChooseCompanyProject
import com.example.bigbenssignin.loginui.LoginScreen

@Composable
fun navigaiton() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = navigationDestenations.loginRoute) {
        LoginScreen(navController)
        ChooseCompanyProject(navController)
    }
}
