package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val AbinetDarkColorScheme = darkColorScheme(
    primary = Burgundy80,
    onPrimary = AbinetOnPrimaryContainer,
    primaryContainer = AbinetPrimary,
    onPrimaryContainer = AbinetPrimaryContainer,
    secondary = Gold80,
    onSecondary = AbinetOnGoldContainer,
    secondaryContainer = Gold40,
    onSecondaryContainer = AbinetGoldContainer,
    background = Color(0xFF1C1917),
    surface = Color(0xFF262220),
    onBackground = ParchmentBackground,
    onSurface = ParchmentBackground
)

private val AbinetLightColorScheme = lightColorScheme(
    primary = AbinetPrimary,
    onPrimary = AbinetOnPrimary,
    primaryContainer = AbinetPrimaryContainer,
    onPrimaryContainer = AbinetOnPrimaryContainer,
    secondary = AbinetGold,
    onSecondary = Color.White,
    secondaryContainer = AbinetGoldContainer,
    onSecondaryContainer = AbinetOnGoldContainer,
    background = ParchmentBackground,
    surface = ParchmentSurface,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F)
)

@Composable
fun AbinetTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep default consistent brand identity
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> AbinetDarkColorScheme
        else -> AbinetLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
