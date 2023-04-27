package com.example.bigbenssignin.loginui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.bigbenssignin.SuccessState
import com.example.bigbenssignin.Features.LoginToProcoreFeature.Presentation.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginToProCoreScaffold(
    authorisationCode :State<String>,
    onEventFunction: (onEvent)->Unit,
    LoginFail : Flow<String>,
    navigateToNextScreen: ()-> Unit,
    successState: Flow<SuccessState<Unit>>,
    modifier: Modifier = Modifier
) {
    val state = rememberCoroutineScope()
    val snackbarState = remember{ SnackbarHostState()}
    NavigateToSelectCompanyOnSuccessState(successState, navigateToNextScreen)

    remember {state.launch{LoginFail.collect{snackbarState.showSnackbar(it)}}}

    val enabled =  remember(key1 = authorisationCode) {
        derivedStateOf {
            // this is to disable the login button until the text field has text in it
            authorisationCode.value.length > 1
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarState)
        }
    ) {paddingValues ->
        helpDialog()
        DialogInstructionsForRetreivingTokin()
        Column(
            modifier = Modifier

                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { navigateToNextScreen() }) {}
            gotoCustomTabsButton()
            Spacer(modifier = Modifier.height(50.dp))
            TokenTextBox(authorisationCode.value) { code ->
                (onEventFunction)(onEvent.updateAuthorisationCode(code))
            }
            Spacer(modifier = Modifier.height(50.dp))
            LoginWithTokinUserCollectedFromProcore(
                sendAuthorisationToProcoreServerForToken = { onEventFunction(onEvent.gettokin) },
                enabled = enabled.value
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TokenTextBox(
    token: String,
    updateAuthorisationCode: (String)->Unit
) {
    val focusManager = LocalFocusManager.current
    TextField(
        value = token,
        onValueChange ={updateAuthorisationCode(it)},
        keyboardActions = KeyboardActions(onDone = {focusManager.clearFocus()}),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        placeholder = { Text(text = "Place your code here!")},
        maxLines = 1,
        modifier = Modifier.width(280.dp),
    )
}