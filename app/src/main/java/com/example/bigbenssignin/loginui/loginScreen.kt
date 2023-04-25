package com.example.bigbenssignin.loginui

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun Home(
    //viewModel: loginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val url = "https://sandbox.procore.com/oauth/authorize?response_type=code&client_id=a44cf8828079de367227e762bc58871d49000c35b361f13bcd9bc0e1ef4a44b1&redirect_uri=urn:ietf:wg:oauth:2.0:oob"
    val  builder = CustomTabsIntent.Builder()
    val customTabsIntent = builder.build()

    Button(onClick = { customTabsIntent.launchUrl(context, Uri.parse(url)) }
    ) {
        Text(text = "launch Intent")
    }
}

@Composable
fun getTokin() {
    // TextField(value = , onValueChange ={} )
}