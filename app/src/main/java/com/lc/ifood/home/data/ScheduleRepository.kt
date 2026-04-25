package com.lc.ifood.home.data

import com.lc.ifood.home.ui.MealSchedule
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun getMealSchedules(): Flow<List<MealSchedule>>
    suspend fun updateMealSchedule(schedule: MealSchedule)
    suspend fun seedDefaultsIfEmpty()
}
