package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.Meal
import com.lc.ifood.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMealsBySchedulesUseCase @Inject constructor(
    private val repository: ScheduleRepository
) {
    operator fun invoke(): Flow<List<Meal>> = repository.getMealSchedules()
        .map { schedules -> schedules.map { it.meal } }
}
