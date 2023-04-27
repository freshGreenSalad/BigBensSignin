package com.example.bigbenssignin.features.loginToProcoreFeature.presentation

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.bigbenssignin.R
import com.example.bigbenssignin.keys

@Composable
fun LoginWithTokenUserCollectedFromProcore(sendAuthorisationToProcoreServerForToken: ()->Unit, enableLoginButton: Boolean )
{
    LoginButton(
        onclick = { sendAuthorisationToProcoreServerForToken()  },
        content = {Text(
            text = stringResource(R.string.loginToBigBenApp),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )},
        enabled = enableLoginButton
    )
}

@Composable
fun GotoCustomTabsButton() {
    val context = LocalContext.current
    val url = "https://sandbox.procore.com/oauth/authorize?response_type=code&client_id=" +
            keys().client_id +
            "&redirect_uri=urn:ietf:wg:oauth:2.0:oob"
    val  builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()

    LoginButton(
        onclick = { customTabsIntent.launchUrl(context, Uri.parse(url)) },
        content = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.LoginToProcore),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Icon(
                    painter = painterResource(id = R.drawable.construction),
                    contentDescription = stringResource( R.string.ConstructionIconDescription),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    )
}

@Composable
fun LoginButton(
    onclick:()->Unit,
    content: @Composable ()-> Unit,
    enabled: Boolean = true
) {
    Row(
        Modifier
            .clip(RoundedCornerShape(8.dp))
            .width(280.dp)
            .height(60.dp)
            .clickable(enabled = enabled) { onclick() }
            .background(MaterialTheme.colorScheme.primary)
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ){
        content()
    }
}