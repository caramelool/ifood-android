package com.lc.ifood.data.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MealRecommendationResponse(
    @Json(name = "userName") val userName: String?,
    @Json(name = "mealType") val mealType: String,
    @Json(name = "placeName") val placeName: String,
    @Json(name = "placeAddress") val placeAddress: String,
    @Json(name = "mealName") val mealName: String,
    @Json(name = "mealDescription") val mealDescription: String,
    @Json(name = "mealPrice") val mealPrice: Double,
    @Json(name = "preferences") val preferences: List<String>,
    @Json(name = "mealImageUrl") val mealImageUrl: String
)
