package com.example.bigbenssignin.Features.LoginToProcoreFeature.Presentation

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
fun helpDialog() {
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
            title = {
                Text(
                style = MaterialTheme.typography.titleMedium,
                text = "Login Help")
            },
            onDismissRequest = { hideDialog() } ,
            text = { Text(text = stringResource(id = R.string.InstrucitonsForLogin)) },
            confirmButton = { TextButton(
                onClick = {hideDialog()},
                content = { Text("Awesome!") }
            )
            }
        )
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
