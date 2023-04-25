package com.example.bigbenssignin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.bigbenssignin.applicationState.ApplictionState
import com.example.bigbenssignin.ui.theme.BigBensSigninTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BigBensSigninTheme {
                ApplictionState()
            }
        }
    }
}




