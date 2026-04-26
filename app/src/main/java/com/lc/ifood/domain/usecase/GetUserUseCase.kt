package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.User
import com.lc.ifood.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<User?> = repository.getUser()
}
