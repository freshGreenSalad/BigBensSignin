package com.example.bigbenssignin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bigbenssignin.common.data.dataStore.ProfileKeyIdentifiers
import com.example.bigbenssignin.features.scaffoldDrawer.presentation.HomeNavGraph
import com.example.bigbenssignin.features.scaffoldDrawer.presentation.viewModel.DrawerScaffoldViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun NavGraphBuilder.homeSubGraph(){
    composable(route = "Graph.HOME") {
    val viewModel = hiltViewModel<DrawerScaffoldViewModel>()
        home(profileKeyIdentifiers = viewModel.keyLoginData.collectAsState().value)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun home(navController: NavHostController = rememberNavController(), profileKeyIdentifiers: ProfileKeyIdentifiers) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    bendrawer(drawerState){
        Scaffold(topBar = {
            benTopBar("title") {
                scope.launch {
                    drawerState.open()
                }
            }
        }) {
            Box(Modifier.padding(it)) {
                HomeNavGraph(navController, profileKeyIdentifiers)
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
