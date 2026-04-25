package com.lc.ifood.home.data

import com.lc.ifood.core.data.db.dao.MealScheduleDao
import com.lc.ifood.core.data.db.entity.MealScheduleEntity
import com.lc.ifood.core.domain.model.MealType
import com.lc.ifood.home.ui.MealSchedule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScheduleRepositoryImpl @Inject constructor(
    private val dao: MealScheduleDao
) : ScheduleRepository {

    override fun getMealSchedules(): Flow<List<MealSchedule>> =
        dao.getAll().map { entities ->
            if (entities.isEmpty()) defaultSchedules() else entities.map { it.toDomain() }
        }

    override suspend fun updateMealSchedule(schedule: MealSchedule) {
        dao.upsert(schedule.toEntity())
    }

    override suspend fun seedDefaultsIfEmpty() {
        if (dao.count() == 0) {
            dao.insertAll(defaultSchedules().map { it.toEntity() })
        }
    }

    private fun MealScheduleEntity.toDomain() = MealSchedule(
        mealType = MealType.valueOf(mealType),
        label = label,
        hour = hour,
        minute = minute,
        period = if (hour < 12) "am" else "pm"
    )

    private fun MealSchedule.toEntity() = MealScheduleEntity(
        mealType = mealType.name,
        label = label,
        hour = hour,
        minute = minute
    )

    private fun defaultSchedules() = listOf(
        MealSchedule(MealType.BREAKFAST, "Café da Manhã", 8, 0, "am"),
        MealSchedule(MealType.LUNCH, "Almoço", 13, 0, "pm"),
        MealSchedule(MealType.AFTERNOON_SNACK, "Lanche da Tarde", 17, 0, "pm"),
        MealSchedule(MealType.DINNER, "Jantar", 21, 0, "pm")
    )
}
