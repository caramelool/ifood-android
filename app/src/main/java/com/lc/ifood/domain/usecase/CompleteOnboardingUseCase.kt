package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.repository.OnboardingRepository
import javax.inject.Inject

/**
 * Marks the onboarding flow as completed so the app navigates directly to the home screen
 * on subsequent launches.
 *
 * Should be called once the user finishes all onboarding steps and confirms their profile.
 */
class CompleteOnboardingUseCase @Inject constructor(
    private val repository: OnboardingRepository
) {
    suspend operator fun invoke() {
        repository.setOnboardingCompleted()
    }
}