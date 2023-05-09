package com.example.bigbenssignin

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BenDrawer(drawerState:DrawerState,navigateToLoginPage: ()-> Unit, navigateToCompanyProject: ()-> Unit, submitAllTimeSheets: () -> Unit, content : @Composable () -> Unit, ) {
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                NavigationDrawerItem(
                    label = { Text("logout") } ,
                    selected = false,
                    onClick = {
                        scope.launch {
                            navigateToLoginPage()
                            drawerState.close()
                        }
                    }
                )

                NavigationDrawerItem(
                    label =  { Text("change company project") } ,
                    selected = false,
                    onClick = {
                        scope.launch {
                            navigateToCompanyProject()
                            drawerState.close()
                        }
                    }
                )

                NavigationDrawerItem(label =  { Text("submit daily timesheets") } , selected = false, onClick = { scope.launch {
                    submitAllTimeSheets()
                    drawerState.close()
                } })
            }
        }
    ) {
        content()
    }
}