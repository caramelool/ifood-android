package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.repository.MealReminderRepository
import javax.inject.Inject

class SendMealReminderUseCase @Inject constructor(
    private val repository: MealReminderRepository
) {
    suspend operator fun invoke(schedule: MealSchedule, preferences: List<String>) =
        repository.sendReminder(schedule, preferences)
}
