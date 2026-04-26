package com.lc.ifood.di

import com.lc.ifood.data.db.AppDatabase
import com.lc.ifood.data.db.dao.MealScheduleDao
import com.lc.ifood.data.db.dao.UserDao
import com.lc.ifood.data.db.dao.UserPreferenceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun provideMealScheduleDao(db: AppDatabase): MealScheduleDao = db.mealScheduleDao()

    @Provides
    fun provideUserPreferenceDao(db: AppDatabase): UserPreferenceDao = db.userPreferenceDao()

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao = db.userDao()
}
