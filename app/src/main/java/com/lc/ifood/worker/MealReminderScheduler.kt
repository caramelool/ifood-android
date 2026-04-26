package com.lc.ifood.worker

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.usecase.GetMealSchedulesUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealReminderScheduler @Inject constructor(
    @ApplicationContext private val context: Context,
    private val getMealSchedules: GetMealSchedulesUseCase,
) {
    suspend fun scheduleAll() {
        getMealSchedules.invoke().collect { schedules ->
            schedules.forEach { schedule -> schedule(schedule) }
        }
    }

    fun schedule(schedule: MealSchedule) {
        val alarmManager = context.getSystemService(AlarmManager::class.java)
        val pendingIntent = buildPendingIntent(schedule)
        val triggerTime = triggerTimeMillis(schedule.hour, schedule.minute)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
        } else {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
        }
    }

    private fun buildPendingIntent(schedule: MealSchedule): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(MealReminderWorker.KEY_MEAL_TYPE, schedule.meal.type.name)
            putExtra(MealReminderWorker.KEY_HOUR, schedule.hour)
            putExtra(MealReminderWorker.KEY_MINUTE, schedule.minute)
        }
        return PendingIntent.getBroadcast(
            context,
            schedule.meal.type.ordinal,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }

    companion object {
        fun triggerTimeMillis(hour: Int, minute: Int): Long {
            val target = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                add(Calendar.MINUTE, -30)
            }
            if (target.timeInMillis <= System.currentTimeMillis()) {
                target.add(Calendar.DAY_OF_YEAR, 1)
            }
            return target.timeInMillis
        }
    }
}
