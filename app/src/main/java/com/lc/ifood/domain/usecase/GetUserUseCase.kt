package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.User
import com.lc.ifood.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Emits the current [User] profile, or `null` if no user has been saved yet.
 *
 * Callers can use a `null` emission to determine that onboarding has not been completed
 * or that the user profile still needs to be created.
 *
 * @return a [Flow] that emits whenever the stored user profile changes.
 */
class GetUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(): Flow<User?> = repository.getUser()
}
