package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.repository.OnboardingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Emits whether the user has already completed the onboarding flow.
 *
 * Used by [com.lc.ifood.ui.splash.SplashViewModel] to decide whether to navigate to
 * the onboarding screen or directly to the home screen on app launch.
 *
 * @return a [Flow] that emits `true` once onboarding is marked as completed.
 */
class GetOnboardingStatusUseCase @Inject constructor(
    private val repository: OnboardingRepository
) {
    operator fun invoke(): Flow<Boolean> = repository.isOnboardingCompleted
}