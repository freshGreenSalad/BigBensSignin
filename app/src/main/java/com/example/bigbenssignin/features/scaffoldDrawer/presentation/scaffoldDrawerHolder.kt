package com.example.bigbenssignin.features.scaffoldDrawer.presentation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bigbenssignin.R
import com.example.bigbenssignin.common.data.dataStore.ProfileKeyIdentifiers
import com.example.bigbenssignin.common.domain.SuccessState
import com.example.bigbenssignin.common.presentaiton.NavigationDestinations
import com.example.bigbenssignin.features.scaffoldDrawer.presentation.viewModel.DrawerScaffoldViewModel
import com.example.bigbenssignin.features.scaffoldDrawer.presentation.viewModel.OnEventScaffoldViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun NavGraphBuilder.loggedInSubGraph(navigateToLoginPage: ()-> Unit){
    composable(route = NavigationDestinations.LoggedInSubGraph) {
        val viewModel = hiltViewModel<DrawerScaffoldViewModel>()

        ScaffoldDrawerHolder(
            profileKeyIdentifiers = viewModel.keyLoginData.collectAsState().value,
            navigateToLoginPage = {
                (viewModel::onEvent)(OnEventScaffoldViewModel.DeleteEverythingDatastore)
                navigateToLoginPage()
            },
            submitAllTimeSheets = { (viewModel::onEvent)(OnEventScaffoldViewModel.SubmitAllTimeSheets) },
            successState = viewModel.channel
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldDrawerHolder(
    navController: NavHostController = rememberNavController(),
    profileKeyIdentifiers: ProfileKeyIdentifiers,
    navigateToLoginPage: ()-> Unit,
    submitAllTimeSheets: () -> Unit,
    successState: Flow<SuccessState<Unit>>,
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackbarState = remember{ SnackbarHostState()}

    LaunchedEffect(Unit){
        successState.collect{successFlow ->
            when (successFlow){
                is SuccessState.Failure -> {
                    snackbarState.showSnackbar(successFlow.error?:"alls good")
                }
                is SuccessState.Success -> {}
            }
        }
    }

    val title = navController.currentBackStackEntryAsState().value?.destination?.route.toString()

    BenDrawer(
        drawerState = drawerState,
        navigateToLoginPage = navigateToLoginPage,
        navigateToCompanyProject = { navController.navigate(NavigationDestinations.SelectCompany) },
        submitAllTimeSheets = submitAllTimeSheets
    ){
        Scaffold(topBar = {
            benTopBar(title) {
                scope.launch {
                    drawerState.open()
                }
            }
        },
        snackbarHost = {SnackbarHost(hostState = snackbarState)}
            ) {
            Box(Modifier.padding(it)) {
                LoggedInSubNavGraph(navController, profileKeyIdentifiers,snackbarState)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun benTopBar(title:String, onClick: ()-> Unit) {
    TopAppBar(
        title = {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){Text(title)}},
        navigationIcon = { Icon(
            modifier = Modifier.clickable { onClick() },
            painter = painterResource(id = R.drawable.menu),
            contentDescription = "" )}
    )
}
