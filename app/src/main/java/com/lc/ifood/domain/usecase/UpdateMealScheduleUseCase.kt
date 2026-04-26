package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.repository.MealScheduleRepository
import com.lc.ifood.worker.MealReminderScheduler
import javax.inject.Inject

class UpdateMealScheduleUseCase @Inject constructor(
    private val repository: MealScheduleRepository,
    private val mealReminderScheduler: MealReminderScheduler
) {
    suspend operator fun invoke(schedule: MealSchedule) {
        repository.updateMealSchedule(schedule)
        mealReminderScheduler.schedule(schedule)
    }
}
