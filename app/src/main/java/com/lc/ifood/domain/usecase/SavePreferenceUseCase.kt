package com.lc.ifood.domain.usecase

import com.lc.ifood.data.factory.MealFactory
import com.lc.ifood.domain.model.MealType
import com.lc.ifood.domain.model.UserPreference
import com.lc.ifood.domain.repository.PreferenceRepository
import javax.inject.Inject

class SavePreferenceUseCase @Inject constructor(
    private val repository: PreferenceRepository,
    private val factory: MealFactory
) {
    suspend operator fun invoke(label: String, mealTypes: List<MealType>) {
        val preference = UserPreference(
            id = 0,
            label = label,
            meals = mealTypes.map { factory.factoryMeal(it) }
        )
        repository.addPreference(preference)
    }
}
