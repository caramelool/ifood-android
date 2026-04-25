package com.lc.ifood.home.domain

import com.lc.ifood.home.data.PreferenceRepository
import javax.inject.Inject

class DeletePreferenceUseCase @Inject constructor(
    private val repository: PreferenceRepository
) {
    suspend operator fun invoke(id: Int) = repository.deletePreference(id)
}
