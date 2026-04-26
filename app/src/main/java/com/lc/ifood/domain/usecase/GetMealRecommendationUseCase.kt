package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.MealRecommendation
import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.model.UserPreference
import com.lc.ifood.domain.repository.MealRecommendationRepository
import javax.inject.Inject

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
