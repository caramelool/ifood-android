package com.lc.ifood.ui.splash

import android.Manifest
import android.app.Activity
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lc.ifood.R

@Composable
fun SplashScreen(
    onNavigateToOnboarding: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val destination by viewModel.destination.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var permissionGranted by remember { mutableStateOf(Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) }
    var showPermissionError by remember { mutableStateOf(false) }

    val launcher = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) permissionGranted = true else showPermissionError = true
        }
    } else null

    LaunchedEffect(Unit) {
        launcher?.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

    LaunchedEffect(destination, permissionGranted) {
        if (permissionGranted && destination != SplashDestination.Loading) {
            when (destination) {
                SplashDestination.Home -> onNavigateToHome()
                SplashDestination.Onboarding -> onNavigateToOnboarding()
                SplashDestination.Loading -> Unit
            }
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
        AlertDialog(
            onDismissRequest = {},
            title = { Text(stringResource(R.string.splash_notification_required_title)) },
            text = { Text(stringResource(R.string.splash_notification_required_message)) },
            confirmButton = {
                TextButton(onClick = onPermissionErrorConfirm) {
                    Text(stringResource(R.string.splash_notification_required_confirm))
                }
            }
        )
    }
}
