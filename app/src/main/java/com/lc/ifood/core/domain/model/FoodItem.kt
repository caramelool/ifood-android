package com.lc.ifood.core.domain.model

data class FoodItem(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val mealType: MealType,
    val imageUrl: String? = null
)

data class MealCategory(
    val type: MealType,
    val label: String,
    val timeDescription: String
)
