package com.lc.ifood.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lc.ifood.data.db.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users LIMIT 1")
    fun getUser(): Flow<UserEntity?>

    @Insert
    suspend fun insert(user: UserEntity)
}
