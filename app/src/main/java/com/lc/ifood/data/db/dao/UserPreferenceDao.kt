package com.lc.ifood.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lc.ifood.data.db.entity.UserPreferenceEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO for reading and writing [UserPreferenceEntity] rows.
 *
 * Meal types are stored as a comma-separated string (e.g. `"BREAKFAST,LUNCH"`) in the
 * `mealTypes` column. [getByMealType] uses four LIKE patterns to match a given meal type
 * regardless of whether it appears at the start, end, middle, or as the sole value —
 * without accidentally matching partial names (e.g. `"LUNCH"` must not match `"AFTERNOON_SNACK"`).
 */
@Dao
interface UserPreferenceDao {

    /** Emits the full list of preferences whenever any row changes. */
    @Query("SELECT * FROM user_preferences")
    fun getAll(): Flow<List<UserPreferenceEntity>>

    @Insert
    suspend fun insert(preference: UserPreferenceEntity)

    @Query("DELETE FROM user_preferences WHERE id = :id")
    suspend fun deleteById(id: Int)

    /**
     * Returns all preferences associated with the given [mealType] name.
     *
     * The four WHERE clauses handle all positions of the value within the CSV:
     * - exact match (only value)
     * - starts with the value followed by a comma
     * - ends with a comma followed by the value
     * - surrounded by commas on both sides
     *
     * @param mealType the [com.lc.ifood.domain.model.MealType] name to filter by (e.g. `"BREAKFAST"`)
     */
    @Query("""
        SELECT * FROM user_preferences
        WHERE mealTypes = :mealType
           OR mealTypes LIKE :mealType || ',%'
           OR mealTypes LIKE '%,' || :mealType
           OR mealTypes LIKE '%,' || :mealType || ',%'
    """)
    suspend fun getByMealType(mealType: String): List<UserPreferenceEntity>
}
