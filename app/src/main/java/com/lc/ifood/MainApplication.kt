package com.lc.ifood

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.work.Configuration
import com.lc.ifood.worker.MealReminderScheduler
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application(), Configuration.Provider,
    LifecycleOwner by ProcessLifecycleOwner.get() {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var mealReminderScheduler: MealReminderScheduler

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        lifecycleScope.launch {
            mealReminderScheduler.scheduleAll()
        }
    }
}
