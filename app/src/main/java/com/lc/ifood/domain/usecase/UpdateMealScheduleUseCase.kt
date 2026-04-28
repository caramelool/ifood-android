package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.repository.MealScheduleRepository
import javax.inject.Inject

/**
 * Persists an updated [MealSchedule] to the local database.
 *
 * The caller (typically [com.lc.ifood.ui.schedule.ScheduleAdjustmentViewModel]) is responsible
 * for triggering an alarm reschedule after this use case completes; without it the old
 * AlarmManager entry would fire at the previous time even after the user changes the schedule.
 *
 * @param schedule the updated meal schedule to persist.
 */
class UpdateMealScheduleUseCase @Inject constructor(
    private val repository: MealScheduleRepository
) {
    suspend operator fun invoke(schedule: MealSchedule) {
        repository.updateMealSchedule(schedule)
    }
}
