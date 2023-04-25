package com.example.bigbenssignin.loginui

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bigbenssignin.keys

@Composable
fun Home(
    viewModel: loginViewModel = hiltViewModel()
) {
    val enabled =  remember(key1 = viewModel.authorisationCode) {
        derivedStateOf {
            viewModel.authorisationCode.value.length > 10
        }
    }

    DialogInstructionsForRetreivingTokin()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        gotoCustomTabsButton()
        Spacer(modifier = Modifier.height(50.dp))
        TokenTextBox(viewModel.authorisationCode.value) { code ->
            (viewModel::onEvent)(onEvent.updateAuthorisationCode(code))
        }
        Spacer(modifier = Modifier.height(50.dp))
        LoginWithTokinUserCollectedFromProcore(
            sendAuthorisationToProcoreServerForToken = { viewModel.onEvent(onEvent.gettokin) },
            enabled = enabled.value
        )
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
            onDismissRequest = { showdialog.value = false },
            text = {Text(text = "Gidday thanks for using bens login service, to get set up you need to login to Procore with this app and copy the code Procore gives you. Then paste the code in code box and click login")},
            confirmButton = {
                TextButton(onClick = { showdialog.value = false })
                {Text(text = "Lets Go!") }
            }
        )
    }
}
