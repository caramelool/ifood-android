package com.lc.ifood.worker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

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
