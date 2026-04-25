package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.repository.ScheduleRepository
import com.lc.ifood.worker.MealReminderScheduler
import javax.inject.Inject

class UpdateMealScheduleUseCase @Inject constructor(
    private val repository: ScheduleRepository,
    private val mealReminderScheduler: MealReminderScheduler
) {
    suspend operator fun invoke(schedule: MealSchedule) {
        repository.updateMealSchedule(schedule)
        mealReminderScheduler.schedule(schedule)
    }
}
