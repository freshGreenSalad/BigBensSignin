package com.example.bigbenssignin.features.loginToProcoreFeature.presentation

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.bigbenssignin.common.domain.SuccessState
import com.example.bigbenssignin.features.loginToProcoreFeature.presentation.*
import com.example.bigbenssignin.features.loginToProcoreFeature.presentation.viewmodel.LoginEvents
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun LoginToProCore(
    authorisationCode :State<String>,
    onEventFunction: (LoginEvents)->Unit,
    navigateToNextScreen: ()-> Unit,
    successState: Flow<SuccessState<Unit>>,
) {
    val snackbarState = remember{ SnackbarHostState()}

    LaunchedEffect(Unit){
        successState.collect{successFlow ->
            when (successFlow){
                is SuccessState.Failure -> {
                    snackbarState.showSnackbar(successFlow.error?:"alls good")
                }
                is SuccessState.Success -> {
                    navigateToNextScreen()
                }
            }
        }
    }


    val enableLoginButton =  remember(key1 = authorisationCode) {
        derivedStateOf {
            // this is to disable the login button until the text field has text in it
            authorisationCode.value.length > 1
        }
    }
    LoginToProCoreScaffold(
        snackbarState = snackbarState,
        authorisationCode = authorisationCode,
        onEventFunction = onEventFunction,
        enableLoginButton = enableLoginButton
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginToProCoreScaffold(
    snackbarState: SnackbarHostState,
    authorisationCode :State<String>,
    onEventFunction: (LoginEvents)->Unit,
    enableLoginButton: State<Boolean>
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarState)
        }
    ) {paddingValues ->
        HelpDialog()
        DialogInstructionsForRetrievingToken()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GotoCustomTabsButton()
            Spacer(modifier = Modifier.height(50.dp))
            TokenTextBox(authorisationCode.value) { code ->
                (onEventFunction)(LoginEvents.UpdateAuthorisationCode(code))
            }
            Spacer(modifier = Modifier.height(50.dp))
            LoginWithTokenUserCollectedFromProcore(
                sendAuthorisationToProcoreServerForToken = { onEventFunction(LoginEvents.GetToken) },
                enableLoginButton = enableLoginButton.value
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