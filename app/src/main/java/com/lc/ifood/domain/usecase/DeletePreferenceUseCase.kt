package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.repository.PreferenceRepository
import javax.inject.Inject

class DeletePreferenceUseCase @Inject constructor(
    private val repository: PreferenceRepository
) {
    suspend operator fun invoke(id: Int) = repository.deletePreference(id)
}
