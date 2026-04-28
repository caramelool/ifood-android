package com.lc.ifood.di

import com.lc.ifood.data.permission.NotificationPermissionCheckerImpl
import com.lc.ifood.domain.permission.NotificationPermissionChecker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PermissionModule {

    @Binds
    @Singleton
    abstract fun bindNotificationPermissionChecker(
        impl: NotificationPermissionCheckerImpl
    ): NotificationPermissionChecker
}
