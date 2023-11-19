package com.jasslin.utility2_1.ui.theme

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
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Red80,
    onPrimary = Red20,
    primaryContainer = Red30,
    onPrimaryContainer = Red90,
    inversePrimary = Red40,
    secondary = DarkRed80,
    onSecondary = DarkRed20,
    secondaryContainer = DarkRed30,
    onSecondaryContainer = DarkRed90,
    tertiary = ChampagnePink80,
    onTertiary = ChampagnePink20,
    tertiaryContainer = ChampagnePink30,
    onTertiaryContainer = ChampagnePink90,
    error = RosyBrown80,
    onError = RosyBrown20,
    errorContainer = RosyBrown30,
    onErrorContainer = RosyBrown90,
    background = Grey10,
    onBackground = Grey90,
    surface = RedGrey30,
    onSurface = RedGrey80,
    inverseSurface = Grey90,
    inverseOnSurface = Grey10,
    surfaceVariant = RedGrey30,
    onSurfaceVariant = RedGrey80,
    outline = RedGrey80
)

private val LightColorScheme = lightColorScheme(
    primary = Red40,
    onPrimary = Color.White,
    primaryContainer = Red90,
    onPrimaryContainer = Red10,
    inversePrimary = Red80,
    secondary = DarkRed40,
    onSecondary = Color.White,
    secondaryContainer = DarkRed90,
    onSecondaryContainer = DarkRed10,
    tertiary = ChampagnePink40,
    onTertiary = Color.White,
    tertiaryContainer = ChampagnePink90,
    onTertiaryContainer = ChampagnePink10,
    error = RosyBrown40,
    onError = Color.White,
    errorContainer = RosyBrown90,
    onErrorContainer = RosyBrown10,
    background = Grey99,
    onBackground = Grey10,
    surface = RedGrey90,
    onSurface = RedGrey30,
    inverseSurface = Grey20,
    inverseOnSurface = Grey95,
    surfaceVariant = RedGrey90,
    onSurfaceVariant = RedGrey30,
    outline = RedGrey40
)



@Composable
fun Utility21Theme(
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
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}