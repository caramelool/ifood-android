package com.lc.ifood.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MealRecommendationResponse(
    @param:Json(name = "userName") val userName: String?,
    @param:Json(name = "mealType") val mealType: String,
    @param:Json(name = "placeName") val placeName: String,
    @param:Json(name = "placeAddress") val placeAddress: String,
    @param:Json(name = "mealName") val mealName: String,
    @param:Json(name = "mealDescription") val mealDescription: String,
    @param:Json(name = "mealPrice") val mealPrice: Double,
    @param:Json(name = "preferences") val preferences: List<String>,
    @param:Json(name = "mealImageUrl") val mealImageUrl: String
)
