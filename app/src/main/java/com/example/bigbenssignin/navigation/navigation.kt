package com.example.bigbenssignin.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bigbenssignin.chooseCompany.ChooseCompany
import com.example.bigbenssignin.loginui.LoginScreen

@Composable
fun navigaiton() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "friendslist") {
        LoginScreen()
        composable("chooseCompany") {
            ChooseCompany()
        }
    }
}
