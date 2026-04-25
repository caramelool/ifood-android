package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.repository.OnboardingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOnboardingStatusUseCase @Inject constructor(
    private val repository: OnboardingRepository
) {
    operator fun invoke(): Flow<Boolean> = repository.isOnboardingCompleted
}