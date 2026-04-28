package com.lc.ifood.data.permission

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.lc.ifood.domain.permission.NotificationPermissionChecker
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationPermissionCheckerImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : NotificationPermissionChecker {

    override fun isGranted(): Boolean =
        checkVersionCode() || checkPermissionAlreadyGranted()

    private fun checkVersionCode() = Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun checkPermissionAlreadyGranted() =
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
}
