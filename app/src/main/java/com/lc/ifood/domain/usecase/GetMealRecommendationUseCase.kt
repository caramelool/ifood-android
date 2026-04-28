package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.MealRecommendation
import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.model.UserPreference
import com.lc.ifood.domain.repository.MealRecommendationRepository
import javax.inject.Inject

/**
 * Fetches a meal recommendation from the backend for the given schedule and dietary preferences.
 *
 * Extracts [UserPreference.label] strings before delegating to the repository, keeping the
 * remote API contract (plain strings) decoupled from the domain model.
 *
 * @param schedule the meal slot and time to fetch a recommendation for.
 * @param preferences the user's dietary preferences applicable to this meal type.
 * @return a [MealRecommendation] returned by the backend.
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
