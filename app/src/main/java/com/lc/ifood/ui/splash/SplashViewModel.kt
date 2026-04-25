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
