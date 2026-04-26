package com.lc.ifood.domain.repository

import com.lc.ifood.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(): Flow<User?>
    suspend fun saveUser(name: String)
}
