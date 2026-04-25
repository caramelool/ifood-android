package com.lc.ifood.data.remote

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MealReminderRequest(
    val mealType: String,
    val mealLabel: String,
    val preferences: List<String>
)
