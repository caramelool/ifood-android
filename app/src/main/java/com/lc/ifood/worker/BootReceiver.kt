package com.lc.ifood.worker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var mealRecommendationScheduler: MealRecommendationScheduler

    override fun onReceive(context: Context, intent: Intent) {
        val pendingResult = goAsync()
        try {
            if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
                scheduleAllRecommendations()
            }
        } finally {
            pendingResult.finish()
        }
    }

    private fun scheduleAllRecommendations() {
        CoroutineScope(Dispatchers.IO).launch {
            mealRecommendationScheduler.scheduleAll()
        }
    }
}
