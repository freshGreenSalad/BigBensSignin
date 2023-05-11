package com.example.bigbenssignin.features.chooseCompanyFeature.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bigbenssignin.common.domain.SuccessState
import com.example.bigbenssignin.common.presentaiton.NavigationDestinations
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.models.Companies
import com.example.bigbenssignin.features.chooseCompanyFeature.domain.models.Project
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.People
import kotlinx.coroutines.flow.Flow

fun NavGraphBuilder.chooseCompanyProject(navController: NavController){
    composable(NavigationDestinations.SelectCompany) {
        val viewModel: ChooseCompanyProjectViewModel = hiltViewModel()
        ChooseCompany(
            navigate = {
                navController.navigate(NavigationDestinations.SigninSignout){
                        popUpTo(NavigationDestinations.LoggedInSubGraph){inclusive = true}
                       }
                       },
            listCompanies = viewModel.companiesList,
            listProjects = viewModel.projectsList,
            selectCompany = viewModel::choseCompanyProject,
            successState = viewModel.uiChannel
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

    LaunchedEffect(Unit){
        successState.collect{successFlow ->
            when (successFlow){
                is SuccessState.Failure -> {

                }
                is SuccessState.Success -> {
                    navigate()
                }
            }
        }

    }

    Column {
        Divider(
            Modifier
                .fillMaxWidth()
                .height(1.dp))
        Text(
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.alpha(0.75f).padding(16.dp),
            text = "Choose your Company"
        )

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.requiredHeight(100.dp)
        ) {
            items(listCompanies.value) { company ->
                CompanyProjectCard(
                    {
                        selectCompany(ChooseCompanyProjectEvent.GetListOfProjects(company.id.toString()))
                    },
                    company.name
                )
            }
        }

        AnimatedVisibility(listProjects.value.isNotEmpty()){
            Text(
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.alpha(0.75f).padding(16.dp),
                text = "Choose your Project"
            )
        }


        AnimatedVisibility(visible = showProjects.value) {
            LazyColumn(modifier = Modifier.height(400.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                items(listProjects.value) { project ->
                    CompanyProjectCard(
                        {
                            selectCompany(ChooseCompanyProjectEvent.ChooseProject(project.id.toString()))
                        },
                        project.name
                    )
                }
            }
        }
    }
}

@Composable
fun CompanyProjectCard(
    onclick:()->Unit,
    text:String
) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .height(80.dp)
            .width(300.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable {
                onclick()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(text = text,style = MaterialTheme.typography.headlineLarge)
    }
}