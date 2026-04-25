package com.lc.ifood.domain.model

data class UserPreference(
    val id: Int,
    val label: String,
    val mealTypes: List<MealType> = emptyList()
)