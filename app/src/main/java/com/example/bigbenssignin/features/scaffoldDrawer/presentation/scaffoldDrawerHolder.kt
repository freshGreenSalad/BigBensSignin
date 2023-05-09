package com.example.bigbenssignin.features.scaffoldDrawer.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bigbenssignin.BenDrawer
import com.example.bigbenssignin.R
import com.example.bigbenssignin.common.data.dataStore.ProfileKeyIdentifiers
import com.example.bigbenssignin.common.presentaiton.NavigationDestinations
import com.example.bigbenssignin.features.scaffoldDrawer.presentation.viewModel.DrawerScaffoldViewModel
import com.example.bigbenssignin.features.scaffoldDrawer.presentation.viewModel.OnEventScaffoldViewModel
import kotlinx.coroutines.launch

fun NavGraphBuilder.homeSubGraph( navigateToLoginPage: ()-> Unit){
    composable(route = NavigationDestinations.LoggedInSubGraph) {
        val viewModel = hiltViewModel<DrawerScaffoldViewModel>()
        ScaffoldDrawerHolder(
            profileKeyIdentifiers = viewModel.keyLoginData.collectAsState().value,
            navigateToLoginPage = {
                (viewModel::onEvent)(OnEventScaffoldViewModel.DeleteEverythingDatastore)
                navigateToLoginPage()
            },
            submitAllTimeSheets = { (viewModel::onEvent)(OnEventScaffoldViewModel.SubmitAllTimeSheets) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldDrawerHolder(
    navController: NavHostController = rememberNavController(),
    profileKeyIdentifiers: ProfileKeyIdentifiers,
    navigateToLoginPage: ()-> Unit,
    submitAllTimeSheets: () -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackbarState = remember{ SnackbarHostState()}

    BenDrawer(
        drawerState = drawerState,
        navigateToLoginPage = navigateToLoginPage,
        navigateToCompanyProject = { navController.navigate(NavigationDestinations.SelectCompany) },
        submitAllTimeSheets = submitAllTimeSheets
    ){
        Scaffold(topBar = {
            benTopBar("title") {
                scope.launch {
                    drawerState.open()
                }
            }
        },
        snackbarHost = {SnackbarHost(hostState = snackbarState)}
            ) {
            Box(Modifier.padding(it)) {
                HomeNavGraph(navController, profileKeyIdentifiers,snackbarState)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun benTopBar(title:String, onClick: ()-> Unit) {
    TopAppBar(
        title = {Text(title)},
        navigationIcon = { Icon(
            modifier = Modifier.clickable { onClick() },
            painter = painterResource(id = R.drawable.menu),
            contentDescription = "" )}
    )
}
