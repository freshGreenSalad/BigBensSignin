package com.example.bigbenssignin.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.example.bigbenssignin.app.applicationState.ApplicationState
import com.example.bigbenssignin.ui.theme.BigBensSigninTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BigBensSigninTheme(dynamicColor = false) {
                Surface{
                    ApplicationState()
                }
            }
        }
    }
}




