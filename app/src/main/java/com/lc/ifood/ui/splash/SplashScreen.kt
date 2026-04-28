package com.lc.ifood.ui.splash

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lc.ifood.ui.PermissionDeniedDialog

@Composable
fun SplashScreen(
    onNavigateToOnboarding: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val destination by viewModel.destination.collectAsStateWithLifecycle()
    var showPermissionError by remember { mutableStateOf(false) }

    LaunchedEffect(destination) {
        when (destination) {
            SplashDestination.Home -> onNavigateToHome()
            SplashDestination.Onboarding -> onNavigateToOnboarding()
            SplashDestination.PermissionDenied -> showPermissionError = true
            SplashDestination.Loading -> Unit
        }
    }

    SplashContent(
        showPermissionError = showPermissionError,
        onPermissionErrorConfirm = { (context as? Activity)?.finish() }
    )
}

@Composable
internal fun SplashContent(
    showPermissionError: Boolean,
    onPermissionErrorConfirm: () -> Unit
) {
    if (showPermissionError) {
        PermissionDeniedDialog(onConfirm = onPermissionErrorConfirm)
    }
}
