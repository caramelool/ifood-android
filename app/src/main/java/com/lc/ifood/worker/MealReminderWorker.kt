package com.lc.ifood.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.model.MealType
import com.lc.ifood.domain.repository.MealReminderRepository
import com.lc.ifood.domain.repository.PreferenceRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class MealReminderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val preferenceRepository: PreferenceRepository,
    private val mealReminderRepository: MealReminderRepository,
    private val mealReminderScheduler: MealReminderScheduler
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val KEY_MEAL_TYPE = "meal_type"
        const val KEY_MEAL_LABEL = "meal_label"
        const val KEY_HOUR = "hour"
        const val KEY_MINUTE = "minute"
        const val CHANNEL_ID = "meal_reminders"
        const val CHANNEL_NAME = "Lembretes de Refeição"
        private const val TAG = "MealReminderWorker"
    }

    override suspend fun doWork(): Result {
        val schedule = inputData.toMealSchedule() ?: return Result.failure()

        val preferences = preferenceRepository
            .getPreferencesByMealType(schedule.mealType)
            .map { it.label }

        runCatching {
            mealReminderRepository.sendReminder(schedule, preferences)
        }.onFailure { Log.e(TAG, "API call failed for ${schedule.mealType}", it) }

        createNotificationChannel()
        showNotification(schedule.label)
        mealReminderScheduler.schedule(schedule)

        return Result.success()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Lembretes para horários de refeição"
        }
        applicationContext.getSystemService(NotificationManager::class.java)
            .createNotificationChannel(channel)
    }

    private fun showNotification(mealLabel: String) {
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_popup_reminder)
            .setContentTitle("Hora de comer!")
            .setContentText("Que tal pedir seu $mealLabel agora?")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()
        applicationContext.getSystemService(NotificationManager::class.java)
            .notify(mealLabel.hashCode(), notification)
    }
}

fun MealSchedule.toWorkData() = workDataOf(
    MealReminderWorker.KEY_MEAL_TYPE to mealType.name,
    MealReminderWorker.KEY_MEAL_LABEL to label,
    MealReminderWorker.KEY_HOUR to hour,
    MealReminderWorker.KEY_MINUTE to minute
)

fun Data.toMealSchedule(): MealSchedule? {
    val mealType = getString(MealReminderWorker.KEY_MEAL_TYPE) ?: return null
    val label = getString(MealReminderWorker.KEY_MEAL_LABEL) ?: return null
    val hour = getInt(MealReminderWorker.KEY_HOUR, -1).takeIf { it >= 0 } ?: return null
    val minute = getInt(MealReminderWorker.KEY_MINUTE, 0)
    return MealSchedule(MealType.valueOf(mealType), label, hour, minute)
}
