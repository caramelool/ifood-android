package com.lc.ifood.data.repository

import com.lc.ifood.data.remote.MealReminderApiService
import com.lc.ifood.data.remote.MealReminderRequest
import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.repository.MealReminderRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealReminderRepositoryImpl @Inject constructor(
    private val apiService: MealReminderApiService
) : MealReminderRepository {

    override suspend fun sendReminder(schedule: MealSchedule, preferences: List<String>) {
        apiService.sendMealReminder(
            MealReminderRequest(
                mealType = schedule.meal.type.name,
                mealLabel = schedule.meal.label,
                preferences = preferences
            )
        )
    }
}
