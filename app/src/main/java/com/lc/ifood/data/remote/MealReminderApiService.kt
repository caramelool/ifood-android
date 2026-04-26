package com.lc.ifood.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface MealReminderApiService {
    @GET("recommendation")
    suspend fun getRecommendation(
        @Query("userName") userName: String,
        @Query("mealType") mealType: String,
        @Query("preferences") preferences: List<String>
    ): MealRecommendationResponse
}
