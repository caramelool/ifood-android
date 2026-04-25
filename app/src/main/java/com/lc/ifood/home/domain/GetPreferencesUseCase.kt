package com.lc.ifood.home.domain

import com.lc.ifood.home.data.PreferenceRepository
import com.lc.ifood.home.ui.UserPreference
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPreferencesUseCase @Inject constructor(
    private val repository: PreferenceRepository
) {
    operator fun invoke(): Flow<List<UserPreference>> = repository.getPreferences()
}
