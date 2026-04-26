package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.repository.ScheduleRepository
import javax.inject.Inject

class SeedDefaultSchedulesUseCase @Inject constructor(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke() = repository.seedDefaultsIfEmpty()
}
