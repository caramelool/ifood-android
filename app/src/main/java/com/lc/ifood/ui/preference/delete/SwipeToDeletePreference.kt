package com.lc.ifood.ui.preference.delete

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.lc.ifood.R
import com.lc.ifood.domain.model.UserPreference
import com.lc.ifood.ui.theme.IfoodRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeToDeletePreference(
    preference: UserPreference,
    state: DeletePreferenceState,
    viewModel: DeletePreferenceViewModel = hiltViewModel(),
    content: @Composable () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { newValue ->
            if (newValue == SwipeToDismissBoxValue.EndToStart) {
                state.requestDelete(preference)
                false
            } else {
                true
            }
        },
        positionalThreshold = { totalDistance -> totalDistance * 0.2f }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        enableDismissFromEndToStart = true,
        backgroundContent = {
            val isSwiping = runCatching { dismissState.requireOffset() }.getOrElse { 0f } < 0f
            DeleteBackground(
                isActive = dismissState.targetValue == SwipeToDismissBoxValue.EndToStart || isSwiping
            )
        }
    ) {
        content()
    }

    if (state.pendingPreference?.id == preference.id) {
        DeleteConfirmationDialog(
            preferenceName = preference.label,
            onConfirm = {
                state.confirmDelete()?.let { viewModel.delete(it.id) }
            },
            onDismiss = state::dismiss
        )
    }
}

@Composable
private fun DeleteBackground(isActive: Boolean) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isActive) IfoodRed else Color.Transparent,
        label = "swipe_background_color"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor),
        contentAlignment = Alignment.CenterEnd
    ) {
        if (isActive) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.padding(end = 20.dp)
            )
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    preferenceName: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.delete_preference_dialog_title)) },
        text = {
            Text(text = stringResource(R.string.delete_preference_dialog_message, preferenceName))
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm,
                colors = ButtonDefaults.textButtonColors(contentColor = IfoodRed)
            ) {
                Text(text = stringResource(R.string.delete_preference_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = stringResource(R.string.common_cancel))
            }
        }
    )
}
