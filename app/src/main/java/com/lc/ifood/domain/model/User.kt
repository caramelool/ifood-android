package com.lc.ifood.domain.model

data class User(
    val id: Int,
    val name: String
)

data class UserPreference(
    val id: Int,
    val label: String,
    val mealTypes: List<MealType> = emptyList()
)
