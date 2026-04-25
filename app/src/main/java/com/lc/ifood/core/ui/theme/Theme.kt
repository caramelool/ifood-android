package com.lc.ifood.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val IfoodColorScheme = lightColorScheme(
    primary = IfoodRed,
    onPrimary = IfoodSurface,
    primaryContainer = IfoodRedDark,
    onPrimaryContainer = IfoodSurface,
    background = IfoodBackground,
    surface = IfoodSurface,
    onBackground = IfoodTextPrimary,
    onSurface = IfoodTextPrimary,
    surfaceVariant = IfoodDivider,
    outline = IfoodDivider,
    secondary = IfoodRed,
    onSecondary = IfoodSurface
)

@Composable
fun IfoodTheme(
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = IfoodColorScheme,
        typography = Typography,
        content = content
    )
}
