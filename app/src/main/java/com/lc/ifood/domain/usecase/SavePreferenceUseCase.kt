package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.UserPreference
import com.lc.ifood.domain.repository.PreferenceRepository
import javax.inject.Inject

class SavePreferenceUseCase @Inject constructor(
    private val repository: PreferenceRepository
) {
    suspend operator fun invoke(preference: UserPreference) = repository.addPreference(preference)
}
