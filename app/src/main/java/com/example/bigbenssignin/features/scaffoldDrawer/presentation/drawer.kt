package com.example.bigbenssignin

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun bendrawer(drawerState:DrawerState, content : @Composable () -> Unit) {
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                NavigationDrawerItem(
                    label = { Text("logout") } ,
                    selected = false,
                    onClick = { scope.launch {
                    drawerState.close() }
                    })
                NavigationDrawerItem(label =  { Text("settings") } , selected = false, onClick = { scope.launch {  drawerState.close()} })
                NavigationDrawerItem(label =  { Text("submit daily timesheets") } , selected = false, onClick = { scope.launch {  drawerState.close()} })
            }
        }
    ) {
        content()
    }
}