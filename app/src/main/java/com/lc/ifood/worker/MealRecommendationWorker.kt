package com.lc.ifood.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.lc.ifood.MainActivity
import com.lc.ifood.R
import com.lc.ifood.domain.model.MealRecommendation
import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.model.MealType
import com.lc.ifood.domain.model.labelId
import com.lc.ifood.domain.usecase.GetMealRecommendationUseCase
import com.lc.ifood.domain.usecase.GetPreferencesByMealTypeUseCase
import com.lc.ifood.worker.MealRecommendationWorker.Companion.KEY_HOUR
import com.lc.ifood.worker.MealRecommendationWorker.Companion.KEY_MEAL_TYPE
import com.lc.ifood.worker.MealRecommendationWorker.Companion.KEY_MINUTE
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

/**
 * WorkManager worker that fetches a meal recommendation and posts a notification.
 *
 * Enqueued by [AlarmReceiver] when an alarm fires. The worker:
 * 1. Deserializes the [MealSchedule] from input data.
 * 2. Loads the user's dietary preferences for that meal type.
 * 3. Calls the backend for a recommendation (non-fatal on failure — no notification is shown).
 * 4. Posts an expandable notification with restaurant and dish details.
 * 5. Reschedules the alarm for the next day so the cycle repeats daily.
 *
 * Input data keys: [KEY_MEAL_TYPE], [KEY_HOUR], [KEY_MINUTE].
 */
@HiltWorker
class MealRecommendationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val mealRecommendationScheduler: MealRecommendationScheduler,
    private val getPreferencesByMealType: GetPreferencesByMealTypeUseCase,
    private val getMealRecommendation: GetMealRecommendationUseCase,
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val KEY_MEAL_TYPE = "meal_type"
        const val KEY_HOUR = "hour"
        const val KEY_MINUTE = "minute"
        const val CHANNEL_ID = "meal_reminders"
        const val CHANNEL_NAME = "Lembretes de Refeição"
        private const val TAG = "MealReminderWorker"
    }

    /**
     * Entry point called by WorkManager on a background thread.
     *
     * Returns [Result.failure] if the input data cannot be parsed into a valid [MealSchedule].
     * API errors are caught and logged; the worker still returns [Result.success] so WorkManager
     * does not retry — the next alarm will try again the following day.
     */
    override suspend fun doWork(): Result {
        val schedule = inputData.toMealSchedule() ?: return Result.failure()
        val mealType = schedule.mealType
        val mealLabel = ContextCompat.getString(context, mealType.labelId)

        val preferences = getPreferencesByMealType(mealType)

        val recommendation = runCatching {
            getMealRecommendation(schedule, preferences)
        }.onFailure { Log.e(TAG, "Recommendation failed for $mealType", it) }.getOrNull()

        createNotificationChannel()

        if (recommendation != null) {
            showEnhancedNotification(mealLabel, recommendation)
        }

        mealRecommendationScheduler.schedule(schedule)

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

    private fun showEnhancedNotification(mealLabel: String, rec: MealRecommendation) {
        val priceFormatted = "R$ %.2f".format(rec.mealPrice)
        val bigText = "${rec.mealName} • $priceFormatted\n${rec.placeName}\n${rec.placeAddress}"

        val tapIntent = MainActivity.intentRecommendation(applicationContext, rec)
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            rec.mealType.hashCode(),
            tapIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("Hora de comer! ${rec.mealName}")
            .setContentText("${rec.placeName} • $priceFormatted")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(bigText)
                    .setBigContentTitle("Hora de comer! ${rec.mealName}")
                    .setSummaryText(mealLabel)
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        applicationContext.getSystemService(NotificationManager::class.java)
            .notify(mealLabel.hashCode(), notification)
    }

    fun Data.toMealSchedule(): MealSchedule? {
        val mealType = getString(KEY_MEAL_TYPE)
            ?.let { MealType.valueOf(it) } ?: return null
        val hour = getInt(KEY_HOUR, -1).takeIf { it >= 0 } ?: return null
        val minute = getInt(KEY_MINUTE, 0)
        return MealSchedule(mealType, hour, minute)
    }
}
