package com.lc.ifood.ui.onboarding

data class OnboardingUiState(
    val pages: List<OnboardingPage> = emptyList(),
    val isFabVisible: Boolean = false,
    val isOnboardCompleted: Boolean = false,
)

data class OnboardingPage(
    val title: Int,
    val subtitle: Int,
    val emoji: String
)
