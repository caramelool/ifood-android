package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Persists the user's display name, creating or overwriting the existing profile.
 *
 * Called once during onboarding when the user submits their name for the first time.
 *
 * @param name the display name entered by the user.
 */
class SaveUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(name: String) = repository.saveUser(name)
}
