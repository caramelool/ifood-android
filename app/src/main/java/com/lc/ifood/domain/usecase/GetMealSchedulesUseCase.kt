package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.repository.MealScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMealSchedulesUseCase @Inject constructor(
    private val repository: MealScheduleRepository
) {
    operator fun invoke(): Flow<List<MealSchedule>> = repository.getMealSchedules()
}
