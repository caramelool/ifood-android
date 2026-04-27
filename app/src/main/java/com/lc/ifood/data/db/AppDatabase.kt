package com.lc.ifood.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lc.ifood.data.db.dao.MealScheduleDao
import com.lc.ifood.data.db.dao.UserDao
import com.lc.ifood.data.db.dao.UserPreferenceDao
import com.lc.ifood.data.db.entity.MealScheduleEntity
import com.lc.ifood.data.db.entity.UserEntity
import com.lc.ifood.data.db.entity.UserPreferenceEntity

/**
 * Main Room database for the iFood app.
 *
 * Holds three tables:
 * - [MealScheduleEntity] — one row per [com.lc.ifood.domain.model.MealType], keyed by its name.
 * - [UserPreferenceEntity] — user-defined dietary preferences, each linked to one or more meal types via a CSV column.
 * - [UserEntity] — the single logged-in user profile.
 *
 * Schema version history:
 * - v1: initial schema (meal_schedules + user_preferences)
 * - v2: added `users` table (see [com.lc.ifood.data.db.migration.MIGRATION_1_2])
 *
 * Migrations are registered in [com.lc.ifood.di.AppModule.provideAppDatabase].
 */
@Database(
    entities = [MealScheduleEntity::class, UserPreferenceEntity::class, UserEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mealScheduleDao(): MealScheduleDao
    abstract fun userPreferenceDao(): UserPreferenceDao
    abstract fun userDao(): UserDao
}
