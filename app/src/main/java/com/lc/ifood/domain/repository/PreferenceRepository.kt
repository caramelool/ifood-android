package com.lc.ifood.domain.repository

import com.lc.ifood.domain.model.MealType
import com.lc.ifood.domain.model.UserPreference
import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    fun getPreferences(): Flow<List<UserPreference>>
    suspend fun getPreferencesByMealType(mealType: MealType): List<UserPreference>
    suspend fun addPreference(preference: UserPreference)
    suspend fun deletePreference(id: Int)
}
