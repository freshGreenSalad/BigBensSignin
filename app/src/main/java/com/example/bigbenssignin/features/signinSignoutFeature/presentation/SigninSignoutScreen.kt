package com.example.bigbenssignin.features.signinSignoutFeature.presentation

import android.app.Person
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bigbenssignin.features.signinSignoutFeature.domain.models.People

@Composable
fun SigninSignoutScreen(
    listPeople: State<List<People>>,
    addToRoom:(SigninSignoutEvent)-> Unit
) {
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
                1 -> {LazyListOfSignedInUsers()}
            }
        }

    }
}

@Composable
fun LazyListOfPeople(
    listPeople: State<List<People>>,
    addToRoom:(SigninSignoutEvent)-> Unit
) {
    LazyColumn(){
        items(listPeople.value){person ->
            personCard(person,{addToRoom(SigninSignoutEvent.AddToRoom(person))})
        }
    }
}

@Composable
fun LazyListOfSignedInUsers() {
    Column {
        Text(text = "signedinUsers")
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