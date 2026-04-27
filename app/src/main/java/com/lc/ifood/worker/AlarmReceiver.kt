package com.lc.ifood.worker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * BroadcastReceiver that bridges AlarmManager alarms to WorkManager.
 *
 * When an alarm set by [MealRecommendationScheduler] fires, Android delivers the broadcast here.
 * The receiver extracts the meal schedule extras from the intent and enqueues a one-time
 * [MealRecommendationWorker] so the heavy work (network call, notification) runs off the main
 * thread with WorkManager's retry and lifecycle guarantees.
 *
 * Uses `@AndroidEntryPoint` to allow Hilt field injection of [WorkManager].
 */
@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var workManager: WorkManager

    override fun onReceive(context: Context, intent: Intent) {
        val mealType = intent.getStringExtra(MealRecommendationWorker.KEY_MEAL_TYPE) ?: return
        val hour = intent.getIntExtra(MealRecommendationWorker.KEY_HOUR, -1).takeIf { it >= 0 } ?: return
        val minute = intent.getIntExtra(MealRecommendationWorker.KEY_MINUTE, 0)

        val request = OneTimeWorkRequestBuilder<MealRecommendationWorker>()
            .setInputData(
                workDataOf(
                    MealRecommendationWorker.KEY_MEAL_TYPE to mealType,
                    MealRecommendationWorker.KEY_HOUR to hour,
                    MealRecommendationWorker.KEY_MINUTE to minute,
                )
            )
            .build()

        workManager.enqueue(request)
    }
}
