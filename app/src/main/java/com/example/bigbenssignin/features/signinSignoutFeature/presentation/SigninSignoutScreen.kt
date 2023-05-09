package com.example.bigbenssignin.features.signinSignoutFeature.presentation

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
        TabRow(selectedTabIndex = selectedTabIndex.value) {
            Tab(selected = true, content = {
                Box(Modifier.height(60.dp)) {
                    Text(text = "Signin")
                }
            }, onClick = {
                selectedTabIndex.value = 0
            })
            Tab(selected = true, content = {
                Box(Modifier.height(60.dp)) {
                    Text(text = "Signout")
                }
            }, onClick = {
                selectedTabIndex.value = 1
            })
        }
        Crossfade(targetState = selectedTabIndex.value) {index ->
            when(index){
                0 -> {LazyListOfPeople(listPeople,addToRoom)}
                1 -> {LazyListOfSignedInUsers(signedInWorkerList,addToRoom) }
            }
        }

    }
}

@Composable
fun LazyListOfPeople(
    listPeople: Flow<List<People>>,
    addToRoom:(SigninSignoutEvent)-> Unit
) {
    val notyetsignedin = listPeople.collectAsStateWithLifecycle(initialValue = emptyList())
    LazyColumn(){
        items(notyetsignedin.value){person ->
            personCard(person,{
                addToRoom(SigninSignoutEvent.AddTimesheetToRoom(person))
                addToRoom(SigninSignoutEvent.AddPersonToRoom(person))
            })
        }
    }
}

@Composable
fun LazyListOfSignedInUsers(signedinWorkerList:Flow<List<People>>, removeFromRoom:(SigninSignoutEvent)-> Unit) {

    val signedinWorkerListState = signedinWorkerList.collectAsStateWithLifecycle(initialValue = emptyList())
    LazyColumn(){
        items(signedinWorkerListState.value){person ->
            personCard(person,{
                removeFromRoom(SigninSignoutEvent.Logout(person))
            })
        }
    }
}

@Composable
fun personCard(
    person: People,
    addToRoom:()-> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(MaterialTheme.colorScheme.primary)
            .clickable {
                addToRoom()
            }
    ) {
        Row(Modifier.fillMaxWidth()) {
            Text(text = person.name ?: "no name")
        }
    }
}