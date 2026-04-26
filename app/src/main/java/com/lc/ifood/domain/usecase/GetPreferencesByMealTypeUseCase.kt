package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.MealType
import com.lc.ifood.domain.model.UserPreference
import com.lc.ifood.domain.repository.PreferenceRepository
import javax.inject.Inject

class GetPreferencesByMealTypeUseCase @Inject constructor(
    private val repository: PreferenceRepository
) {
    suspend operator fun invoke(mealType: MealType): List<UserPreference> =
        repository.getPreferencesByMealType(mealType)
}
