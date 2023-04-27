package com.example.bigbenssignin.features.chooseCompanyFeature.presentation

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bigbenssignin.common.presentaiton.NavigationDestinations
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.models.Companies

fun NavGraphBuilder.chooseCompanyProject(navController: NavController){
    composable(NavigationDestinations.SelectCompany) {
        val viewModel: ChooseCompanyProjectViewModel = hiltViewModel()
        ChooseCompany(navController, viewModel.companiesList.value, )
    }
}

@Composable
fun ChooseCompany(
    navController: NavController,
    listCompanies: List<Companies>
) {

    LazyColumn(){
        items(listCompanies){

        }
    }
}