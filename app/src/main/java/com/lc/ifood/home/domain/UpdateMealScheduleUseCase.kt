package com.lc.ifood.home.domain

import com.lc.ifood.home.data.ScheduleRepository
import com.lc.ifood.home.ui.MealSchedule
import javax.inject.Inject

class UpdateMealScheduleUseCase @Inject constructor(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke(schedule: MealSchedule) = repository.updateMealSchedule(schedule)
}
