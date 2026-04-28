package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.MealType
import com.lc.ifood.domain.model.UserPreference
import com.lc.ifood.domain.repository.PreferenceRepository
import javax.inject.Inject

/**
 * Creates a new [UserPreference] and persists it to the local database.
 *
 * The `id` is set to `0` so Room auto-generates the primary key on insertion.
 *
 * @param label the display name of the dietary preference (e.g. "Vegetarian").
 * @param mealTypes the meal types this preference applies to.
 */
class SavePreferenceUseCase @Inject constructor(
    private val repository: PreferenceRepository
) {
    suspend operator fun invoke(label: String, mealTypes: List<MealType>) {
        val preference = UserPreference(
            id = 0,
            label = label,
            mealTypes = mealTypes
        )
        repository.addPreference(preference)
    }
}
