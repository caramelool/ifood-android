package com.lc.ifood.home.domain

import com.lc.ifood.home.data.ScheduleRepository
import com.lc.ifood.home.ui.MealSchedule
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMealSchedulesUseCase @Inject constructor(
    private val repository: ScheduleRepository
) {
    operator fun invoke(): Flow<List<MealSchedule>> = repository.getMealSchedules()
}
