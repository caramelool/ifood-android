package com.lc.ifood.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.lc.ifood.data.db.entity.MealScheduleEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for reading and writing [MealScheduleEntity] rows.
 *
 * The `meal_schedules` table uses `mealType` (a [com.lc.ifood.domain.model.MealType] name) as its
 * primary key, so there is exactly one row per meal type at most.
 */
@Dao
interface MealScheduleDao {

    /** Emits the full list of schedules whenever any row changes. */
    @Query("SELECT * FROM meal_schedules")
    fun getAll(): Flow<List<MealScheduleEntity>>

    /**
     * Inserts or replaces the given schedule.
     *
     * Because `mealType` is the primary key, calling this with an existing meal type overwrites
     * the previous time without creating a duplicate row.
     */
    @Upsert
    suspend fun upsert(schedule: MealScheduleEntity)

    /** Bulk-inserts a list of schedules; used exclusively for seeding defaults. */
    @Insert
    suspend fun insertAll(schedules: List<MealScheduleEntity>)

    /**
     * Returns the total number of rows.
     *
     * Used by [com.lc.ifood.data.repository.MealScheduleRepositoryImpl.seedDefaultsIfEmpty] to
     * decide whether defaults need to be written on first launch.
     */
    @Query("SELECT COUNT(*) FROM meal_schedules")
    suspend fun count(): Int

    /**
     * One-shot read of all schedules (no Flow).
     *
     * Use this when you need the current snapshot without subscribing to updates — for example,
     * when scheduling alarms at startup.
     */
    @Query("SELECT * FROM meal_schedules")
    suspend fun getAllOnce(): List<MealScheduleEntity>
}
