package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.MealType
import com.lc.ifood.domain.repository.MealScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Emits the ordered list of [MealType] values derived from the stored meal schedules.
 *
 * Transforms [MealSchedule] objects into plain [MealType] values so callers that only
 * need to know which meal types exist do not have to deal with schedule details.
 *
 * @return a [Flow] that emits a new list whenever the underlying schedules change.
 */
class GetMealsUseCase @Inject constructor(
    private val repository: MealScheduleRepository
) {
    operator fun invoke(): Flow<List<MealType>> = repository.getMealSchedules()
        .map { schedules -> schedules.map { it.mealType } }
}
