package com.example.bigbenssignin.Features.LoginToProcoreFeature.Presentation

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.bigbenssignin.keys

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