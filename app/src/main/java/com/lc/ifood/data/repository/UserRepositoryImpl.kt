package com.lc.ifood.data.repository

import com.lc.ifood.data.db.dao.UserDao
import com.lc.ifood.data.db.entity.UserEntity
import com.lc.ifood.domain.model.User
import com.lc.ifood.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val dao: UserDao
) : UserRepository {

    private var user: User? = null

    override fun getUser(): Flow<User?> {
        if (user != null) {
            return flowOf(user)
        }
        return dao.getUser().map { entity ->
            entity?.toDomain()
                .also { user = it }
        }
    }

    override suspend fun saveUser(name: String) {
        val entity = UserEntity(name = name)
        dao.insert(entity)
        user = entity.toDomain()
    }

    private fun UserEntity.toDomain() = User(id = id, name = name)
}
