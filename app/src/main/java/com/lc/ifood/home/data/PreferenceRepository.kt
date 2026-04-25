package com.lc.ifood.home.data

import com.lc.ifood.home.ui.UserPreference
import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    fun getPreferences(): Flow<List<UserPreference>>
    suspend fun addPreference(preference: UserPreference)
    suspend fun deletePreference(id: Int)
}
