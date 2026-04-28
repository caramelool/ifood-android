package com.lc.ifood.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lc.ifood.domain.permission.NotificationPermissionChecker
import com.lc.ifood.domain.usecase.GetOnboardingStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    checker: NotificationPermissionChecker,
    getOnboardingStatus: GetOnboardingStatusUseCase
) : ViewModel() {

    val destination = combine(
        flowOf(checker.isGranted()),
        getOnboardingStatus()
    ) { permissionGranted, completed ->
        if (completed) {
            if (permissionGranted) SplashDestination.Home else SplashDestination.PermissionDenied
        } else {
            SplashDestination.Onboarding
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SplashDestination.Loading
    )
}
