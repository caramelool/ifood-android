package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.MealType
import com.lc.ifood.domain.model.UserPreference
import com.lc.ifood.domain.repository.PreferenceRepository
import javax.inject.Inject

/**
 * Returns all dietary preferences associated with a specific [MealType].
 *
 * Used to filter the preference list when building a meal recommendation request,
 * so only preferences relevant to the current meal slot are sent to the backend.
 *
 * @param mealType the meal type (e.g. BREAKFAST, LUNCH) to filter preferences by.
 * @return list of [UserPreference] linked to the given [mealType].
 */
class GetPreferencesByMealTypeUseCase @Inject constructor(
    private val repository: PreferenceRepository
) {
    suspend operator fun invoke(mealType: MealType): List<UserPreference> =
        repository.getPreferencesByMealType(mealType)
}
