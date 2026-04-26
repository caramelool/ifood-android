package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.repository.MealScheduleRepository
import javax.inject.Inject

class SeedDefaultSchedulesUseCase @Inject constructor(
    private val repository: MealScheduleRepository
) {
    suspend operator fun invoke() = repository.seedDefaultsIfEmpty()
}
