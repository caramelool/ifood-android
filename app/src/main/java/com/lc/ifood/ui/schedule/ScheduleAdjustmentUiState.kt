package com.lc.ifood.ui.schedule

import com.lc.ifood.domain.model.MealSchedule

data class ScheduleAdjustmentUiState(
    val schedules: List<MealSchedule> = emptyList(),
    val isSaving: Boolean = false,
    val saved: Boolean = false
)
