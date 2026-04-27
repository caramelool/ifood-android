package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.MealType
import com.lc.ifood.domain.repository.MealScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMealsUseCase @Inject constructor(
    private val repository: MealScheduleRepository
) {
    operator fun invoke(): Flow<List<MealType>> = repository.getMealSchedules()
        .map { schedules -> schedules.map { it.mealType } }
}
