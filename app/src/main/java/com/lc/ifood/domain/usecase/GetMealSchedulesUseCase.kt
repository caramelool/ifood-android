package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.repository.MealScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Emits the full list of [MealSchedule] entries stored in the local database.
 *
 * @return a [Flow] that emits a new list whenever any meal schedule is added or updated.
 */
class GetMealSchedulesUseCase @Inject constructor(
    private val repository: MealScheduleRepository
) {
    operator fun invoke(): Flow<List<MealSchedule>> = repository.getMealSchedules()
}
