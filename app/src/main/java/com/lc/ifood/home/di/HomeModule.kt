package com.lc.ifood.home.di

import com.lc.ifood.core.data.db.AppDatabase
import com.lc.ifood.core.data.db.dao.MealScheduleDao
import com.lc.ifood.core.data.db.dao.UserPreferenceDao
import com.lc.ifood.home.data.PreferenceRepository
import com.lc.ifood.home.data.PreferenceRepositoryImpl
import com.lc.ifood.home.data.ScheduleRepository
import com.lc.ifood.home.data.ScheduleRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeModule {

    @Binds
    @Singleton
    abstract fun bindScheduleRepository(impl: ScheduleRepositoryImpl): ScheduleRepository

    @Binds
    @Singleton
    abstract fun bindPreferenceRepository(impl: PreferenceRepositoryImpl): PreferenceRepository

    companion object {
        @Provides
        fun provideMealScheduleDao(db: AppDatabase): MealScheduleDao = db.mealScheduleDao()

        @Provides
        fun provideUserPreferenceDao(db: AppDatabase): UserPreferenceDao = db.userPreferenceDao()
    }
}
