package com.lc.ifood.domain.repository

import com.lc.ifood.domain.model.MealSchedule
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun getMealSchedules(): Flow<List<MealSchedule>>
    suspend fun updateMealSchedule(schedule: MealSchedule)
    suspend fun seedDefaultsIfEmpty()
}
