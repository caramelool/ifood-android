package com.lc.ifood.home.data

import com.lc.ifood.core.data.db.dao.UserPreferenceDao
import com.lc.ifood.core.data.db.entity.UserPreferenceEntity
import com.lc.ifood.core.domain.model.MealType
import com.lc.ifood.home.ui.UserPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceRepositoryImpl @Inject constructor(
    private val dao: UserPreferenceDao
) : PreferenceRepository {

    override fun getPreferences(): Flow<List<UserPreference>> =
        dao.getAll().map { entities -> entities.map { it.toDomain() } }

    override suspend fun addPreference(preference: UserPreference) {
        dao.insert(preference.toEntity())
    }

    override suspend fun deletePreference(id: Int) {
        dao.deleteById(id)
    }

    private fun UserPreferenceEntity.toDomain() = UserPreference(
        id = id,
        label = label,
        mealTypes = if (mealTypes.isBlank()) emptyList()
        else mealTypes.split(",").mapNotNull { runCatching { MealType.valueOf(it) }.getOrNull() }
    )

    private fun UserPreference.toEntity() = UserPreferenceEntity(
        id = id,
        label = label,
        mealTypes = mealTypes.joinToString(",") { it.name }
    )
}
