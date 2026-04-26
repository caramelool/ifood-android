package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.Meal
import com.lc.ifood.domain.repository.PreferenceRepository
import javax.inject.Inject

class SavePreferenceUseCase @Inject constructor(
    private val repository: PreferenceRepository
) {
    suspend operator fun invoke(label: String, meals: List<Meal>) {
        repository.addPreference(
            label = label,
            meals = meals
        )
    }
}
