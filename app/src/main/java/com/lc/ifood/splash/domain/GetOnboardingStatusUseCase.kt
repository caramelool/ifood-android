package com.lc.ifood.splash.domain

import com.lc.ifood.onboarding.domain.OnboardingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOnboardingStatusUseCase @Inject constructor(
    private val repository: OnboardingRepository
) {
    operator fun invoke(): Flow<Boolean> = repository.isOnboardingCompleted
}
