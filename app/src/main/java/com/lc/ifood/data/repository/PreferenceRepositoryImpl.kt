package com.lc.ifood.data.repository

import com.lc.ifood.data.db.dao.UserPreferenceDao
import com.lc.ifood.data.db.entity.UserPreferenceEntity
import com.lc.ifood.domain.factory.MealFactory
import com.lc.ifood.domain.model.MealType
import com.lc.ifood.domain.model.UserPreference
import com.lc.ifood.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferenceRepositoryImpl @Inject constructor(
    private val dao: UserPreferenceDao,
    private val mealFactory: MealFactory
) : PreferenceRepository {

    override fun getPreferences(): Flow<List<UserPreference>> =
        dao.getAll().map { entities -> entities.map { it.toDomain() } }

    override suspend fun getPreferencesByMealType(mealType: MealType): List<UserPreference> =
        dao.getByMealType(mealType.name).map { it.toDomain() }

    override suspend fun addPreference(preference: UserPreference) {
        dao.insert(preference.toEntity())
    }

    override suspend fun deletePreference(id: Int) {
        dao.deleteById(id)
    }

    private fun UserPreferenceEntity.toDomain() = UserPreference(
        id = id,
        label = label,
        meals = mealTypes.split(",")
            .map { MealType.valueOf(it) }
            .map { mealFactory.factoryMeal(it) }
    )

    private fun UserPreference.toEntity() = UserPreferenceEntity(
        id = id,
        label = label,
        mealTypes = meals.joinToString(",") { it.type.name }
    )
}
