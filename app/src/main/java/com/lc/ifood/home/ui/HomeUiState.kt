package com.lc.ifood.home.ui

import com.lc.ifood.core.domain.model.MealType

data class MealSchedule(
    val mealType: MealType,
    val label: String,
    val hour: Int,
    val minute: Int = 0,
    val period: String
)

data class UserPreference(
    val id: Int,
    val label: String,
    val mealTypes: List<MealType> = emptyList()
)

data class HomeUiState(
    val mealSchedules: List<MealSchedule> = emptyList(),
    val preferences: List<UserPreference> = emptyList()
)
