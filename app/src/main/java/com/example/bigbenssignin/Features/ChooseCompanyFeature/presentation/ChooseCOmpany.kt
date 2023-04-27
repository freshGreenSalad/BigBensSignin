package com.example.bigbenssignin.ChooseCompanyFeature

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bigbenssignin.Common.Presentaiton.navigationDestenations


fun NavGraphBuilder.ChooseCompanyProject(navController: NavController){
    composable(navigationDestenations.SelecteCompany) {
        val viewModel: chooseCompanyProjectViewModel = hiltViewModel()
        ChooseCompany(navController, viewModel)
    }
}

@Composable
fun ChooseCompany(
    navController: NavController,
    viewModel:chooseCompanyProjectViewModel
) {
    Text(viewModel.companysList.value.toString())
}