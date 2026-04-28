package com.lc.ifood.ui.onboarding

data class OnboardingUiState(
    val pages: List<OnboardingPage> = emptyList(),
    val isFabVisible: Boolean = false,
    val isLastPage: Boolean = false,
)

data class OnboardingPage(
    val title: Int,
    val subtitle: Int,
    val emoji: String
)

sealed interface OnboardingEvent {
    data object RequestNotificationPermission : OnboardingEvent
    data object NavigateToHome : OnboardingEvent
    data object ShowPermissionDeniedDialog : OnboardingEvent
}
