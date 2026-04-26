package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.mapper.MealMapper
import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.model.MealType
import javax.inject.Inject

class CreateMealScheduleUseCase @Inject constructor(
    private val mealMapper: MealMapper
) {
    operator fun invoke(mealType: MealType, hour: Int, minute: Int): MealSchedule =
        MealSchedule(mealMapper.map(mealType), hour, minute)
}
