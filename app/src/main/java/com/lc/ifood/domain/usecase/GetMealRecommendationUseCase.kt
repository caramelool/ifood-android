package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.MealRecommendation
import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.model.UserPreference
import com.lc.ifood.domain.repository.MealRecommendationRepository
import javax.inject.Inject

/**
 * Fetches an AI-powered meal recommendation for the given schedule.
 *
 * Extracts the [UserPreference.label] strings from the domain objects before delegating to the
 * repository, keeping the API contract (plain strings) separate from the domain model.
 *
 * @param schedule the meal slot and time to fetch a recommendation for.
 * @param preferences the user's dietary preferences for this meal type.
 * @return a [MealRecommendation] from the backend.
 */
class GetMealRecommendationUseCase @Inject constructor(
    private val repository: MealRecommendationRepository
) {
    suspend operator fun invoke(
        schedule: MealSchedule,
        preferences: List<UserPreference>,
    ): MealRecommendation = repository.getRecommendation(
        schedule,
        preferences.map { it.label }
    )
}
