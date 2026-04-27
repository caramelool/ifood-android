package com.lc.ifood.data.repository

import com.lc.ifood.data.db.dao.MealScheduleDao
import com.lc.ifood.data.db.entity.MealScheduleEntity
import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.model.MealType
import com.lc.ifood.domain.repository.MealScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository implementation for meal schedule persistence.
 *
 * On first launch the database is empty, so [getMealSchedules] returns in-memory defaults
 * rather than an empty list. Calling [seedDefaultsIfEmpty] writes those defaults to the DB
 * so that subsequent edits by the user are persisted.
 *
 * Default schedule times:
 * - BREAKFAST — 08:00
 * - LUNCH — 13:00
 * - AFTERNOON_SNACK — 17:00
 * - DINNER — 21:00
 */
@Singleton
class MealScheduleRepositoryImpl @Inject constructor(
    private val dao: MealScheduleDao
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

    /**
     * Writes the default schedules to the database only if no rows exist yet.
     *
     * This guard prevents overwriting user-customized times on subsequent app launches.
     */
    override suspend fun seedDefaultsIfEmpty() {
        if (dao.count() == 0) {
            dao.insertAll(defaultSchedules().map { it.toEntity() })
        }
    }

    private fun MealScheduleEntity.toDomain(): MealSchedule {
        return MealSchedule(
            mealType = MealType.valueOf(mealType),
            hour = hour,
            minute = minute
        )
    }

    private fun MealSchedule.toEntity() = MealScheduleEntity(
        mealType = mealType.name,
        hour = hour,
        minute = minute
    )

    private fun defaultSchedules() = listOf(
        MealSchedule(MealType.BREAKFAST, 8, 0),
        MealSchedule(MealType.LUNCH, 13, 0),
        MealSchedule(MealType.AFTERNOON_SNACK, 17, 0),
        MealSchedule(MealType.DINNER, 21, 0)
    )
}
