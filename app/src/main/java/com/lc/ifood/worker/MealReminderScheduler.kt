package com.lc.ifood.worker

import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.usecase.GetMealSchedulesUseCase
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealReminderScheduler @Inject constructor(
    private val getMealSchedules: GetMealSchedulesUseCase,
    private val workManager: WorkManager
) {
    suspend fun scheduleAll() {
        getMealSchedules.invoke().collect { schedules ->
            schedules.forEach { schedule ->
                schedule(schedule)
            }
        }
    }

    fun schedule(schedule: MealSchedule) {
        val delay = delayUntilTrigger(schedule.hour, schedule.minute)
        val request = OneTimeWorkRequestBuilder<MealReminderWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(schedule.toWorkData())
            .build()
        workManager.enqueueUniqueWork(
            "MealReminder_${schedule.meal.type.name}",
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    companion object {
        fun delayUntilTrigger(hour: Int, minute: Int): Long {
            val now = Calendar.getInstance()
            val target = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                add(Calendar.MINUTE, -30)
            }
            if (target.timeInMillis <= now.timeInMillis) {
                target.add(Calendar.DAY_OF_YEAR, 1)
            }
            return target.timeInMillis - now.timeInMillis
        }
    }
}
