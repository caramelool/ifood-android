package com.lc.ifood.domain.repository

import com.lc.ifood.domain.model.MealRecommendation
import com.lc.ifood.domain.model.MealSchedule

interface MealRecommendationRepository {
    suspend fun getRecommendation(
        schedule: MealSchedule,
        preferences: List<String>
    ): MealRecommendation
}
