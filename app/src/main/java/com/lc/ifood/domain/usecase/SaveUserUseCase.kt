package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.repository.UserRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(name: String) = repository.saveUser(name)
}
