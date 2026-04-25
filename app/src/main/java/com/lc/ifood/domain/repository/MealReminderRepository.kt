package com.lc.ifood.domain.repository

import com.lc.ifood.domain.model.MealSchedule

interface MealReminderRepository {
    suspend fun sendReminder(schedule: MealSchedule, preferences: List<String>)
}
