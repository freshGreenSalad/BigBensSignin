package com.example.bigbenssignin.features.chooseCompanyFeature.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bigbenssignin.common.domain.SuccessState
import com.example.bigbenssignin.common.presentaiton.NavigationDestinations
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.models.Companies
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.models.Project
import kotlinx.coroutines.flow.Flow

fun NavGraphBuilder.chooseCompanyProject(navController: NavController){
    composable(NavigationDestinations.SelectCompany) {
        val viewModel: ChooseCompanyProjectViewModel = hiltViewModel()
        ChooseCompany(
            navigate = {navController.navigate(NavigationDestinations.LoginLogout)},
            listCompanies = viewModel.companiesList,
            listProjects = viewModel.projectsList,
            selectCompany = viewModel::choseCompanyProject,
            successState = viewModel.navigaitonChannel
        )
    }
}

@Composable
fun ChooseCompany(
    navigate: () -> Unit,
    listCompanies: State<List<Companies>>,
    listProjects: State<List<Project>>,
    selectCompany: (ChooseCompanyProjectEvent) -> Unit,
    successState: Flow<SuccessState<Unit>>
) {

    val showProjects = remember{
        derivedStateOf {
            listProjects.value.isNotEmpty()
        }
    }

    LaunchedEffect(key1 = successState){
        successState.collect{successFlow ->
            when (successFlow){
                is SuccessState.Failure -> {

                }
                is SuccessState.Success -> {
                    navigate()
                }
                else -> {}
            }
        }

    }

    Column {
        LazyRow(verticalAlignment = Alignment.CenterVertically) {
            items(listCompanies.value) { company ->
                Box(
                    Modifier
                        .width(300.dp)
                        .height(80.dp)
                        .padding(16.dp)
                        .clickable {
                            selectCompany(ChooseCompanyProjectEvent.GetListOfProjects(company.id.toString()))
                        }) {
                    Text(text = company.name)
                }
            }
        }

        AnimatedVisibility(visible = showProjects.value) {
            LazyRow(verticalAlignment = Alignment.CenterVertically) {
                items(listProjects.value) { project ->
                    Box(
                        Modifier
                            .width(300.dp)
                            .height(80.dp)
                            .padding(16.dp)
                            .clickable {
                                selectCompany(ChooseCompanyProjectEvent.ChooseProject(project.id.toString()))
                            }) {
                        Text(text = project.name)
                    }
                }
            }
        }
    }
}