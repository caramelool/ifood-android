package com.lc.ifood.home.ui

import com.lc.ifood.core.domain.model.MealType

data class MealSchedule(
    val mealType: MealType,
    val label: String,
    val hour: Int,
    val period: String
)

data class UserPreference(
    val id: Int,
    val label: String
)

data class HomeUiState(
    val mealSchedules: List<MealSchedule> = defaultMealSchedules(),
    val preferences: List<UserPreference> = defaultPreferences()
)

private fun defaultMealSchedules() = listOf(
    MealSchedule(MealType.BREAKFAST, "breakfast", 8, "am"),
    MealSchedule(MealType.LUNCH, "lunch", 13, "pm"),
    MealSchedule(MealType.AFTERNOON_SNACK, "afternoon", 17, "pm"),
    MealSchedule(MealType.DINNER, "dinner", 21, "pm")
)

private fun defaultPreferences() = listOf(
    UserPreference(1, "Pão com Ovo"),
    UserPreference(2, "Saudavel"),
    UserPreference(3, "Economia")
)
