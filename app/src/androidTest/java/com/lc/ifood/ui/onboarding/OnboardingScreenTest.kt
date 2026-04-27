package com.lc.ifood.ui.onboarding

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lc.ifood.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OnboardingScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    private val pages = listOf(
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
    )

    private fun setContent(uiState: OnboardingUiState) {
        composeRule.setContent {
            OnboardingContent(
                uiState = uiState,
                onPageChanged = {},
                onCompleteOnboarding = {},
                onOnboardingComplete = {}
            )
        }
    }

    @Test
    fun firstPage_displaysEmojiAndTitle() {
        setContent(OnboardingUiState(pages = pages, isFabVisible = false))
        composeRule.onNodeWithText("🎉").assertIsDisplayed()
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.onboarding_title_1))
            .assertIsDisplayed()
    }

    @Test
    fun firstPage_displaysSubtitle() {
        setContent(OnboardingUiState(pages = pages, isFabVisible = false))
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.onboarding_subtitle_1))
            .assertIsDisplayed()
    }

    @Test
    fun fab_showsNextText_whenNotCompleted() {
        setContent(OnboardingUiState(pages = pages, isFabVisible = true, isOnboardCompleted = false))
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.onboarding_btn_next))
            .assertIsDisplayed()
    }

    @Test
    fun fab_showsStartText_whenOnboardCompleted() {
        setContent(OnboardingUiState(pages = pages, isFabVisible = true, isOnboardCompleted = true))
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.onboarding_btn_start))
            .assertIsDisplayed()
    }

    @Test
    fun fab_isNotVisible_whenHidden() {
        setContent(OnboardingUiState(pages = pages, isFabVisible = false, isOnboardCompleted = false))
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.onboarding_btn_next))
            .assertDoesNotExist()
    }
}
