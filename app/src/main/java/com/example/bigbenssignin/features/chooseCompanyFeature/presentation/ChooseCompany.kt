package com.example.bigbenssignin.features.chooseCompanyFeature.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bigbenssignin.common.presentaiton.NavigationDestinations
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.models.Companies
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.models.Project

fun NavGraphBuilder.chooseCompanyProject(navController: NavController){
    composable(NavigationDestinations.SelectCompany) {
        val viewModel: ChooseCompanyProjectViewModel = hiltViewModel()
        ChooseCompany(navController, viewModel.companiesList.value, viewModel.projectsList.value,viewModel::choseCompanyProject)
    }
}

@Composable
fun ChooseCompany(
    navController: NavController,
    listCompanies: List<Companies>,
    listProjects: List<Project>,
    selectCompany: (ChooseCompanyProjectEvent) -> Unit
) {

    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally){
        items(listCompanies){ company ->
            Box(Modifier.width(300.dp).height(80.dp).padding(16.dp).clickable {
                selectCompany(ChooseCompanyProjectEvent.GetListOfProjects(company.id.toString()))
            }) {
                Text(text = company.name)
            }
        }
    }
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally){
        items(listProjects){ project ->
            Box(Modifier.width(300.dp).height(80.dp).padding(16.dp).clickable {
            }) {
                Text(text = project.name)
            }
        }
    }
}