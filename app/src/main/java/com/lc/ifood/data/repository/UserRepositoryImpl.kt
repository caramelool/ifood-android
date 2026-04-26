package com.lc.ifood.data.repository

import com.lc.ifood.data.db.dao.UserDao
import com.lc.ifood.data.db.entity.UserEntity
import com.lc.ifood.domain.model.User
import com.lc.ifood.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val dao: UserDao
) : UserRepository {

    override fun getUser(): Flow<User?> =
        dao.getUser().map { it?.toDomain() }

    override suspend fun saveUser(name: String) {
        dao.insert(UserEntity(name = name))
    }

    private fun UserEntity.toDomain() = User(id = id, name = name)
}
