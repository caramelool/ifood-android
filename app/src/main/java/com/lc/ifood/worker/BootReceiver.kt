package com.lc.ifood.worker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * BroadcastReceiver for the `BOOT_COMPLETED` system event.
 *
 * AlarmManager alarms are cleared when the device reboots. This receiver is the hook point
 * for rescheduling them after boot. The actual rescheduling is currently handled at app startup
 * via [com.lc.ifood.MainApplication.onCreate], so this receiver is intentionally a no-op stub
 * reserved for a future headless-reschedule implementation.
 */
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) = Unit
}
