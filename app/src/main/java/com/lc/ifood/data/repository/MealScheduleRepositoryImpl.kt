package com.lc.ifood.data.repository

import com.lc.ifood.data.db.dao.MealScheduleDao
import com.lc.ifood.data.db.entity.MealScheduleEntity
import com.lc.ifood.domain.mapper.MealMapper
import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.model.MealType
import com.lc.ifood.domain.repository.MealScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealScheduleRepositoryImpl @Inject constructor(
    private val dao: MealScheduleDao,
    private val mealMapper: MealMapper
) : MealScheduleRepository {

    override fun getMealSchedules(): Flow<List<MealSchedule>> =
        dao.getAll().map { entities ->
            if (entities.isEmpty()) {
                defaultSchedules()
            } else {
                entities.map { it.toDomain() }
            }
        }

    override suspend fun updateMealSchedule(schedule: MealSchedule) {
        dao.upsert(schedule.toEntity())
    }

    override suspend fun seedDefaultsIfEmpty() {
        if (dao.count() == 0) {
            dao.insertAll(defaultSchedules().map { it.toEntity() })
        }
    }

    private fun MealScheduleEntity.toDomain(): MealSchedule {
        val type = MealType.valueOf(mealType)
        return MealSchedule(
            meal = mealMapper.map(type),
            hour = hour,
            minute = minute
        )
    }

    private fun MealSchedule.toEntity() = MealScheduleEntity(
        mealType = meal.type.name,
        hour = hour,
        minute = minute
    )

    private fun defaultSchedules() = listOf(
        MealSchedule(mealMapper.map(MealType.BREAKFAST), 8, 0),
        MealSchedule(mealMapper.map(MealType.LUNCH), 13, 0),
        MealSchedule(mealMapper.map(MealType.AFTERNOON_SNACK), 17, 0),
        MealSchedule(mealMapper.map(MealType.DINNER), 21, 0)
    )
}
