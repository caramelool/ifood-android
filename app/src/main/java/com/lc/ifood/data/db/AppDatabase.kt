package com.lc.ifood.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lc.ifood.data.db.dao.MealScheduleDao
import com.lc.ifood.data.db.dao.UserPreferenceDao
import com.lc.ifood.data.db.entity.MealScheduleEntity
import com.lc.ifood.data.db.entity.UserPreferenceEntity

@Database(
    entities = [MealScheduleEntity::class, UserPreferenceEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mealScheduleDao(): MealScheduleDao
    abstract fun userPreferenceDao(): UserPreferenceDao
}
