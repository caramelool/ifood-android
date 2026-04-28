package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.repository.MealScheduleRepository
import javax.inject.Inject

/**
 * Inserts the default meal schedules (Breakfast, Lunch, Dinner) if the database is empty.
 *
 * Intended to run once on first launch via the onboarding flow so the app always has a
 * baseline schedule without requiring manual setup from the user.
 */
class SeedDefaultSchedulesUseCase @Inject constructor(
    private val repository: MealScheduleRepository
) {
    suspend operator fun invoke() = repository.seedDefaultsIfEmpty()
}
