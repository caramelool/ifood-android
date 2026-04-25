package com.lc.ifood.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

interface MealReminderApiService {
    @POST("meal-reminders")
    suspend fun sendMealReminder(@Body request: MealReminderRequest)
}
