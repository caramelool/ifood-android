package com.lc.ifood.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MealSchedule(
    val mealType: MealType,
    val label: String,
    val hour: Int,
    val minute: Int = 0,
    val period: String
)
