package com.lc.ifood.home.domain

import com.lc.ifood.home.data.PreferenceRepository
import com.lc.ifood.home.ui.UserPreference
import javax.inject.Inject

class SavePreferenceUseCase @Inject constructor(
    private val repository: PreferenceRepository
) {
    suspend operator fun invoke(preference: UserPreference) = repository.addPreference(preference)
}
