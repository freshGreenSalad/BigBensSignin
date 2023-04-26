package com.example.bigbenssignin.chooseCompany

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bigbenssignin.navigation.navigationDestenations
import kotlinx.coroutines.flow.collect


fun NavGraphBuilder.ChooseCompanyProject(navController: NavController){
    composable(navigationDestenations.SelecteCompany) {
        val viewModel: chooseCompanyProjectViewModel = hiltViewModel()
        ChooseCompany(navController)
    }
}

@Composable
fun ChooseCompany(
    navController: NavController
) {

}