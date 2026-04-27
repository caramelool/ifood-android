package com.lc.ifood.ui.preference.delete

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.lc.ifood.domain.model.UserPreference

/**
 * Compose state holder that manages the delete-confirmation dialog lifecycle.
 *
 * Marked `@Stable` so Compose knows that reads of its properties are tracked and that the
 * compiler can skip recomposition of callers when none of the observed state has changed.
 *
 * State machine:
 * ```
 * idle  ──requestDelete()──▶  pending
 *                              │
 *                    ┌─────────┴──────────┐
 *               dismiss()          confirmDelete()
 *                    │                    │
 *                  idle               idle (returns item)
 * ```
 */
@Stable
class DeletePreferenceState {
    var pendingPreference: UserPreference? by mutableStateOf(null)
        private set

    /** True when a preference is awaiting user confirmation; controls dialog visibility. */
    val isDialogVisible: Boolean get() = pendingPreference != null

    /** Stores [preference] and shows the confirmation dialog. */
    fun requestDelete(preference: UserPreference) {
        pendingPreference = preference
    }

    /** Dismisses the dialog without deleting anything. */
    fun dismiss() {
        pendingPreference = null
    }

    /**
     * Confirms deletion, clears the pending preference, and returns the item to delete.
     *
     * @return the [UserPreference] that should be deleted, or `null` if none was pending.
     */
    fun confirmDelete(): UserPreference? {
        val toDelete = pendingPreference
        pendingPreference = null
        return toDelete
    }
}

@Composable
fun rememberDeletePreferenceState(): DeletePreferenceState = remember { DeletePreferenceState() }
