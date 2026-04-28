package com.lc.ifood.worker

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.usecase.GetMealSchedulesUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Schedules exact AlarmManager alarms that trigger meal recommendation notifications.
 *
 * Each alarm fires 30 minutes before the scheduled meal time so the user has time to decide
 * where to eat. When the alarm fires, [AlarmReceiver] enqueues a [MealRecommendationWorker]
 * via WorkManager to fetch the recommendation and post the notification.
 *
 * On Android 12+ (API 31+), scheduling exact alarms requires the `SCHEDULE_EXACT_ALARM` permission.
 * If the user has not granted it, [schedule] falls back to [AlarmManager.setAndAllowWhileIdle],
 * which is less precise but does not require the permission.
 *
 * The alarm for each meal type is identified by [MealSchedule.mealType]'s ordinal so that
 * rescheduling replaces the existing alarm instead of creating a duplicate.
 */
@Singleton
class MealRecommendationScheduler @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val getMealSchedules: GetMealSchedulesUseCase
) {
    /**
     * Schedules alarms for all meal types by collecting the current schedules flow once.
     *
     * Called at app startup from [com.lc.ifood.MainApplication.onCreate].
     */
    suspend fun scheduleAll() {
        getMealSchedules.invoke().first().forEach { schedule ->
            schedule(schedule)
        }
    }

    /**
     * Schedules (or replaces) the alarm for a single [MealSchedule].
     *
     * If the computed trigger time is already in the past for today, the alarm is automatically
     * moved to the same time the next day.
     */
    fun schedule(schedule: MealSchedule) {
        val alarmManager = context.getSystemService(AlarmManager::class.java)
        val pendingIntent = buildPendingIntent(schedule)
        val triggerTime = triggerTimeMillis(schedule.hour, schedule.minute)

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
    }

    private fun buildPendingIntent(schedule: MealSchedule): PendingIntent {
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(MealRecommendationWorker.KEY_MEAL_TYPE, schedule.mealType.name)
            putExtra(MealRecommendationWorker.KEY_HOUR, schedule.hour)
            putExtra(MealRecommendationWorker.KEY_MINUTE, schedule.minute)
        }
        return PendingIntent.getBroadcast(
            context,
            schedule.mealType.ordinal,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }

    companion object {
        /**
         * Calculates the alarm trigger time in milliseconds for a meal scheduled at [hour]:[minute].
         *
         * The alarm is set 30 minutes *before* the meal time. If that moment has already passed
         * today, the trigger is moved to the same time tomorrow.
         *
         * @param hour hour of the meal (0–23).
         * @param minute minute of the meal (0–59).
         * @return epoch milliseconds for when the alarm should fire.
         */
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
