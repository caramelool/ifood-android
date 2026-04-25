package com.lc.ifood.ui.splash

sealed interface SplashDestination {
    data object Loading : SplashDestination
    data object Onboarding : SplashDestination
    data object Home : SplashDestination
}
