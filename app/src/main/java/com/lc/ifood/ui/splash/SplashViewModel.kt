package com.lc.ifood.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lc.ifood.domain.usecase.GetOnboardingStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel for the Splash screen.
 *
 * Maps the onboarding completion status from DataStore to a [SplashDestination] that the UI
 * uses to navigate. Starts in [SplashDestination.Loading] until the first value is emitted,
 * then transitions to [SplashDestination.Home] or [SplashDestination.Onboarding].
 *
 * The 5-second [SharingStarted.WhileSubscribed] timeout keeps the upstream DataStore flow active
 * briefly after the composable leaves the composition, avoiding unnecessary restarts during
 * quick configuration changes.
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getOnboardingStatus: GetOnboardingStatusUseCase
) : ViewModel() {

    val destination: StateFlow<SplashDestination> =
        getOnboardingStatus()
            .map { completed ->
                if (completed) SplashDestination.Home else SplashDestination.Onboarding
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SplashDestination.Loading
            )
}
