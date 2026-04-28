package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.UserPreference
import com.lc.ifood.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Emits the full list of the user's dietary preferences.
 *
 * @return a [Flow] that emits a new list whenever preferences are added, updated, or deleted.
 */
class GetPreferencesUseCase @Inject constructor(
    private val repository: PreferenceRepository
) {
    operator fun invoke(): Flow<List<UserPreference>> = repository.getPreferences()
}
