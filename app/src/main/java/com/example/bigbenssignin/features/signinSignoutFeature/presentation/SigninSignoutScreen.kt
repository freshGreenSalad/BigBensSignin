package com.example.bigbenssignin.features.signinSignoutFeature.presentation

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bigbenssignin.common.domain.SuccessState
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.People
import com.example.bigbenssignin.features.signinSignoutFeature.presentation.viewModel.SigninSignoutEvent
import kotlinx.coroutines.flow.Flow

@Composable
fun SigninSignoutScreen(
    listPeople: Flow<List<People>>,
    addToRoom:(SigninSignoutEvent)-> Unit,
    signedInWorkerList:Flow<List<People>>,
    snackbarState: SnackbarHostState,
    successState: Flow<SuccessState<Unit>>,
) {

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

    val selectedTabIndex = remember { mutableStateOf(0) }

    Column(Modifier.fillMaxSize()) {
        Divider(Modifier.fillMaxWidth().height(1.dp))
        TabRow(selectedTabIndex = selectedTabIndex.value) {
            CustomTab("Signin") { selectedTabIndex.value = 0 }
            CustomTab("Signout") { selectedTabIndex.value = 1 }
        }
        Crossfade(targetState = selectedTabIndex.value) {index ->
            when(index){
                0 -> {SiteWorkersNotSignedIn(listPeople,addToRoom)}
                1 -> {SiteWorkersSignedIn(signedInWorkerList,addToRoom) }
            }
        }

    }
}

@Composable
fun CustomTab(text: String, onClick:()->Unit) {
    Tab(
        selected = true,
        content = {
        Box(
            modifier = Modifier.height(60.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text)
        }
    }, onClick = { onClick()})
}

@Composable
fun SiteWorkersNotSignedIn(
    listPeople: Flow<List<People>>,
    addToRoom:(SigninSignoutEvent)-> Unit
) {
    val notYetSignedIn = listPeople.collectAsStateWithLifecycle(initialValue = emptyList())
    LazyColumn(
    ){
        items(notYetSignedIn.value){ person ->
            PersonCard(person) {
                addToRoom(SigninSignoutEvent.AddTimesheetToRoom(person))
                addToRoom(SigninSignoutEvent.AddPersonToRoom(person))
            }
        }
    }
}

@Composable
fun SiteWorkersSignedIn(signedInWorkerList:Flow<List<People>>, removeFromRoom:(SigninSignoutEvent)-> Unit) {
    val signedInWorkerListState = signedInWorkerList.collectAsStateWithLifecycle(initialValue = emptyList())
    LazyColumn(
    ){
        items(signedInWorkerListState.value){ person ->
            PersonCard(person) {
                removeFromRoom(SigninSignoutEvent.Logout(person))
            }
        }
    }
}

@Composable
fun PersonCard(
    person: People,
    addToRoom:()-> Unit
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
                addToRoom()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = person.name ?: "no name",
            style = MaterialTheme.typography.headlineLarge
        )
    }
}