package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.repository.OnboardingRepository
import javax.inject.Inject

class CompleteOnboardingUseCase @Inject constructor(
    private val repository: OnboardingRepository
) {
    suspend operator fun invoke() {
        repository.setOnboardingCompleted()
    }
}