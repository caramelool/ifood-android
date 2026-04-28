package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.repository.PreferenceRepository
import javax.inject.Inject

/**
 * Removes a dietary preference from the local database by its identifier.
 *
 * @param id the unique identifier of the [com.lc.ifood.domain.model.UserPreference] to delete.
 */
class DeletePreferenceUseCase @Inject constructor(
    private val repository: PreferenceRepository
) {
    suspend operator fun invoke(id: Int) = repository.deletePreference(id)
}
