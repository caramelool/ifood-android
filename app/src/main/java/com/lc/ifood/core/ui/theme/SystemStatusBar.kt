package com.lc.ifood.core.ui.theme

import android.annotation.SuppressLint
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext

@SuppressLint("ContextCastToActivity")
@Composable
fun SystemStatusBar(darkIcons: Boolean) {
    val context = LocalContext.current as? ComponentActivity
    DisposableEffect(Unit) {
        context?.enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.Transparent.toArgb()) // Force light icons for dark bg
        )
        onDispose {
            context?.enableEdgeToEdge() // Reset to default on leave
        }
    }
}
