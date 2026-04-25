package com.lc.ifood.core.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.lc.ifood.core.data.db.entity.MealScheduleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealScheduleDao {

    @Query("SELECT * FROM meal_schedules")
    fun getAll(): Flow<List<MealScheduleEntity>>

    @Upsert
    suspend fun upsert(schedule: MealScheduleEntity)

    @Insert
    suspend fun insertAll(schedules: List<MealScheduleEntity>)

    @Query("SELECT COUNT(*) FROM meal_schedules")
    suspend fun count(): Int
}
