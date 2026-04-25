package com.lc.ifood.splash.ui

sealed interface SplashDestination {
    data object Loading : SplashDestination
    data object Onboarding : SplashDestination
    data object Home : SplashDestination
}
