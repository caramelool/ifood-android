package com.lc.ifood.domain.permission

interface NotificationPermissionChecker {
    fun isGranted(): Boolean
}
