package com.example.bigbenssignin.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFD0BCFF),
    secondary = Color(0xFFC5DCC2),
    tertiary = Color(0xFFEFB8C8)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF216c2e),
    onPrimary= Color(0xFFffffff),
    primaryContainer= Color(0xFFa7f5a7),
    onPrimaryContainer= Color(0xFF002106),
    secondary = Color(0xFFffffff),
    onSecondary= Color(0xFFffffff),
    secondaryContainer= Color(0xFFd5e8d0),
    onSecondaryContainer= Color(0xFF101f10),
    tertiary= Color(0xFF39656b),
    onTertiary= Color(0xFFffffff),
    tertiaryContainer= Color(0xFFbcebf2),
    onTertiaryContainer= Color(0xFF001f23),
    background= Color(0xFFfcfdf7),
    onBackground= Color(0xFF1a1c19),
    surface= Color(0xFFfcfdf7),
    onSurface= Color(0xFF1a1c19),
    surfaceVariant= Color(0xFFdee5d9),
    onSurfaceVariant= Color(0xFF424940),
    error= Color(0xFFba1a1a),
    onError= Color(0xFFffffff),
    errorContainer= Color(0xFFffdad6),
    onErrorContainer= Color(0xFF410002),
    outline= Color(0xFF72796f),
)

@Composable
fun BigBensSigninTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}