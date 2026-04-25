package com.lc.ifood.onboarding.ui

import com.lc.ifood.R

data class OnboardingUiState(
    val pages: List<OnboardingPage> = listOf(
        OnboardingPage(
            title = R.string.onboarding_title_1,
            subtitle = R.string.onboarding_subtitle_1,
            emoji = "🎉"
        ),
        OnboardingPage(
            title = R.string.onboarding_title_2,
            subtitle = R.string.onboarding_subtitle_2,
            emoji = "🕐"
        ),
        OnboardingPage(
            title = R.string.onboarding_title_3,
            subtitle = R.string.onboarding_subtitle_3,
            emoji = "🍽️"
        ),
        OnboardingPage(
            title = R.string.onboarding_title_4,
            subtitle = R.string.onboarding_subtitle_4,
            emoji = "🔔"
        )
    ),
    val isFabVisible: Boolean = false,
    val isOnboardCompleted: Boolean = false,
)

data class OnboardingPage(
    val title: Int,
    val subtitle: Int,
    val emoji: String
)
