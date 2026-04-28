package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.repository.MealScheduleRepository
import com.lc.ifood.worker.MealRecommendationScheduler
import javax.inject.Inject

/**
 * Persists a new meal schedule time and immediately reschedules the corresponding alarm.
 *
 * The alarm reschedule is a deliberate side effect: without it the old AlarmManager entry would
 * fire at the previous time even after the user changes the schedule in the UI.
 *
 * @param schedule the updated meal schedule to persist and reschedule.
 */
class UpdateMealScheduleUseCase @Inject constructor(
    private val repository: MealScheduleRepository
) {
    suspend operator fun invoke(schedule: MealSchedule) {
        repository.updateMealSchedule(schedule)
    }
}
