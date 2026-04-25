package com.lc.ifood.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lc.ifood.data.db.entity.UserPreferenceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserPreferenceDao {

    @Query("SELECT * FROM user_preferences")
    fun getAll(): Flow<List<UserPreferenceEntity>>

    @Insert
    suspend fun insert(preference: UserPreferenceEntity)

    @Query("DELETE FROM user_preferences WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("""
        SELECT * FROM user_preferences
        WHERE mealTypes = :mealType
           OR mealTypes LIKE :mealType || ',%'
           OR mealTypes LIKE '%,' || :mealType
           OR mealTypes LIKE '%,' || :mealType || ',%'
    """)
    suspend fun getByMealType(mealType: String): List<UserPreferenceEntity>
}
