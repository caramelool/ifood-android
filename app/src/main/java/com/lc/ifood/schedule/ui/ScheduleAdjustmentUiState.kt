package com.lc.ifood.schedule.ui

import com.lc.ifood.home.ui.MealSchedule

data class ScheduleAdjustmentUiState(
    val schedules: List<MealSchedule> = emptyList(),
    val isSaving: Boolean = false,
    val saved: Boolean = false
)
