package com.lc.ifood.data.repository

import com.lc.ifood.data.remote.MealReminderApiService
import com.lc.ifood.domain.model.MealRecommendation
import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.repository.MealRecommendationRepository
import com.lc.ifood.domain.repository.UserRepository
import kotlinx.coroutines.flow.lastOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealRecommendationRepositoryImpl @Inject constructor(
    private val apiService: MealReminderApiService,
    private val userRepository: UserRepository,
) : MealRecommendationRepository {

    override suspend fun getRecommendation(
        schedule: MealSchedule,
        preferences: List<String>
    ): MealRecommendation {
        val user = userRepository.getUser().lastOrNull()
        requireNotNull(user) { "User not found" }
        val response = apiService.getRecommendation(
            userName = user.name,
            mealType = schedule.meal.type.name.lowercase(),
            preferences = preferences
        )
        return MealRecommendation(
            meal = schedule.meal,
            placeName = response.placeName,
            placeAddress = response.placeAddress,
            mealName = response.mealName,
            mealDescription = response.mealDescription,
            mealPrice = response.mealPrice,
            preferences = response.preferences
        )
    }
}
