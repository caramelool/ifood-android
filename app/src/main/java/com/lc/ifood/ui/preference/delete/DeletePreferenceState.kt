package com.lc.ifood.ui.preference.delete

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.lc.ifood.domain.model.UserPreference

@Stable
class DeletePreferenceState {
    var pendingPreference: UserPreference? by mutableStateOf(null)
        private set

    val isDialogVisible: Boolean get() = pendingPreference != null

    fun requestDelete(preference: UserPreference) {
        pendingPreference = preference
    }

    fun dismiss() {
        pendingPreference = null
    }

    fun confirmDelete(): UserPreference? {
        val toDelete = pendingPreference
        pendingPreference = null
        return toDelete
    }
}

@Composable
fun rememberDeletePreferenceState(): DeletePreferenceState = remember { DeletePreferenceState() }
