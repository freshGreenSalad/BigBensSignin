package com.example.bigbenssignin.features.loginToProcoreFeature.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bigbenssignin.R


@Composable
fun HelpDialog() {
    val showDialog = remember { mutableStateOf(false) }
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
            contentDescription = stringResource(R.string.helpButton)
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
            title = {
                Text(
                style = MaterialTheme.typography.titleMedium,
                text = stringResource(R.string.LoginHelp)
                )
            },
            onDismissRequest = { hideDialog() } ,
            text = { Text(text = stringResource(id = R.string.InstrucitonsForLogin)) },
            confirmButton = { TextButton(
                onClick = {hideDialog()},
                content = { Text(stringResource(R.string.Awesome)) }
            )
            }
        )
    }
}

@Composable
fun DialogInstructionsForRetrievingToken() {
    val showDialog = remember {
        mutableStateOf(true)
    }
    if (showDialog.value) {
        AlertDialog(
            title = {Text(style = MaterialTheme.typography.titleMedium, text = stringResource(R.string.welcome))},
            onDismissRequest = { showDialog.value = false },
            text = {Text(text = stringResource(id = R.string.InstrucitonsForLogin))},
            confirmButton = {
                TextButton(onClick = { showDialog.value = false })
                {Text(text = stringResource(R.string.LetsGo)) }
            }
        )
    }
}
