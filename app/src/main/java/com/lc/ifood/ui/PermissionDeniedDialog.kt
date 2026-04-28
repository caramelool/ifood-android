package com.lc.ifood.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lc.ifood.R

@Composable
fun PermissionDeniedDialog(onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        title = { Text(stringResource(R.string.notification_permission_required_title)) },
        text = { Text(stringResource(R.string.notification_permission_required_message)) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(stringResource(R.string.notification_permission_required_confirm))
            }
        }
    )
}
