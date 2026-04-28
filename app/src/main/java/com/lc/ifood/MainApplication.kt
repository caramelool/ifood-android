package com.lc.ifood

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.work.Configuration
import com.lc.ifood.worker.MealRecommendationScheduler
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Application entry point for the iFood app.
 *
 * Responsibilities:
 * - Triggers Hilt component generation via `@HiltAndroidApp`.
 * - Provides a custom [Configuration] so WorkManager uses [HiltWorkerFactory] and can inject
 *   dependencies into workers (e.g. [com.lc.ifood.worker.MealRecommendationWorker]).
 * - Schedules all meal reminder alarms on every cold start so the alarm chain is never broken.
 *   [com.lc.ifood.worker.BootReceiver] handles rescheduling on headless boot; this covers the
 *   normal app-open path.
 *
 * Implements [LifecycleOwner] via delegation to [ProcessLifecycleOwner] to get access to
 * [lifecycleScope] for the initial alarm scheduling coroutine.
 */
@HiltAndroidApp
class MainApplication : Application(), Configuration.Provider,
    LifecycleOwner by ProcessLifecycleOwner.get() {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var mealRecommendationScheduler: MealRecommendationScheduler

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        lifecycleScope.launch {
            mealRecommendationScheduler.scheduleAll()
        }
    }
}
