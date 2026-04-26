package com.lc.ifood.di

import com.lc.ifood.data.repository.MealReminderRepositoryImpl
import com.lc.ifood.data.repository.OnboardingRepositoryImpl
import com.lc.ifood.data.repository.PreferenceRepositoryImpl
import com.lc.ifood.data.repository.ScheduleRepositoryImpl
import com.lc.ifood.data.repository.UserRepositoryImpl
import com.lc.ifood.domain.repository.MealReminderRepository
import com.lc.ifood.domain.repository.OnboardingRepository
import com.lc.ifood.domain.repository.PreferenceRepository
import com.lc.ifood.domain.repository.ScheduleRepository
import com.lc.ifood.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindScheduleRepository(impl: ScheduleRepositoryImpl): ScheduleRepository

    @Binds
    @Singleton
    abstract fun bindPreferenceRepository(impl: PreferenceRepositoryImpl): PreferenceRepository

    @Binds
    @Singleton
    abstract fun bindOnboardingRepository(impl: OnboardingRepositoryImpl): OnboardingRepository

    @Binds
    @Singleton
    abstract fun bindMealReminderRepository(impl: MealReminderRepositoryImpl): MealReminderRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}
