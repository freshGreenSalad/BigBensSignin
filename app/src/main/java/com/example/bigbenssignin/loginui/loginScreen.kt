package com.example.bigbenssignin.loginui

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.bigbenssignin.R
import com.example.bigbenssignin.keys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun NavGraphBuilder.LoginScreen(){
    composable("friendslist") {
        val viewModel = hiltViewModel<loginViewModel>()
        Home(
            authorisationCode = viewModel.authorisationCode,
            onEventFunction = viewModel::onEvent ,
            LoginFail = viewModel.LoginFailFlow
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    authorisationCode :State<String>,
    onEventFunction: (onEvent)->Unit,
    LoginFail : Flow<String>
) {
    val state = rememberCoroutineScope()
    val snackbarState = remember{ SnackbarHostState()}

    remember {
        state.launch {
            LoginFail.collect {
                snackbarState.showSnackbar(it)
            }
        }
    }

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

@Composable
fun gotoCustomTabsButton() {
    val context = LocalContext.current
    val url = "https://sandbox.procore.com/oauth/authorize?response_type=code&client_id=" +
            keys().client_id +
            "&redirect_uri=urn:ietf:wg:oauth:2.0:oob"
    val  builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()

    ProvideTextStyle(value = MaterialTheme.typography.labelLarge) {
        Row(
            Modifier
                .clip(RoundedCornerShape(8.dp))
                .width(280.dp)
                .height(60.dp)
                .clickable { customTabsIntent.launchUrl(context, Uri.parse(url)) }
                .background(MaterialTheme.colorScheme.primary)
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(text = "Login To Procore")
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

@Composable
fun LoginWithTokinUserCollectedFromProcore(sendAuthorisationToProcoreServerForToken: ()->Unit, enabled: Boolean )
{
    ProvideTextStyle(value = MaterialTheme.typography.labelLarge) {
        Row(
            Modifier
                .clip(RoundedCornerShape(8.dp))
                .width(280.dp)
                .height(60.dp)
                .clickable(enabled = enabled) { sendAuthorisationToProcoreServerForToken() }
                .background(if (enabled) MaterialTheme.colorScheme.primary else Color.Gray)
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(text = "Login!")
        }
    }
}

@Composable
fun DialogInstructionsForRetreivingTokin() {
    val showdialog = remember {
        mutableStateOf(true)
    }
    if (showdialog.value) {
        AlertDialog(
            title = {Text(style = MaterialTheme.typography.titleMedium, text = "Welcome!")},
            onDismissRequest = { showdialog.value = false },
            text = {Text(text = stringResource(id = R.string.InstrucitonsForLogin))},
            confirmButton = {
                TextButton(onClick = { showdialog.value = false })
                {Text(text = "Lets Go!") }
            }
        )
    }
}

@Composable
fun helpDialog() {
    val showDialog = remember {mutableStateOf(false) }
    HelpDialogBox(showDialog = showDialog.value, hideDialog = { showDialog.value = false} )
    Row(
        Modifier
            .fillMaxWidth()
            .height(60.dp), horizontalArrangement = Arrangement.End) {
        Icon(
            modifier = Modifier
                .padding(16.dp)
                .clickable { showDialog.value = true },
            painter = painterResource(id = R.drawable.help),
            contentDescription = "Help Button"
        )
    }
}

@Composable
fun HelpDialogBox(
    showDialog: Boolean,
    hideDialog: ()-> Unit
) {
    if (showDialog) {
        AlertDialog(
            title = {Text(
                style = MaterialTheme.typography.titleMedium,
                text = "Login Help")},
            onDismissRequest = { hideDialog() } ,
            text = {Text(text = stringResource(id = R.string.InstrucitonsForLogin))},
            confirmButton = { TextButton(
                onClick = {hideDialog()},
                content = {Text("Awesome!")}
            )}
        )
    }
}
