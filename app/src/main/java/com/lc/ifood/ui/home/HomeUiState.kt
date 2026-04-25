package com.lc.ifood.ui.home

import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.model.UserPreference

data class HomeUiState(
    val mealSchedules: List<MealSchedule> = emptyList(),
    val preferences: List<UserPreference> = emptyList()
)
