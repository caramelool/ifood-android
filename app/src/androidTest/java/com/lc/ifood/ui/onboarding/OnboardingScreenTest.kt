package com.lc.ifood.ui.onboarding

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lc.ifood.R
import org.junit.Assert.assertTrue
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

    private fun setContent(uiState: OnboardingUiState, onFabClicked: () -> Unit = {}) {
        composeRule.setContent {
            OnboardingContent(
                uiState = uiState,
                onPageChanged = {},
                onFabClicked = onFabClicked
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
    fun fab_showsNextText_whenNotOnLastPage() {
        setContent(OnboardingUiState(pages = pages, isFabVisible = true, isLastPage = false))
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.onboarding_btn_next), useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun fab_showsStartText_whenOnLastPage() {
        setContent(OnboardingUiState(pages = pages, isFabVisible = true, isLastPage = true))
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.onboarding_btn_start), useUnmergedTree = true)
            .assertIsDisplayed()
    }

    @Test
    fun fab_isNotVisible_whenHidden() {
        setContent(OnboardingUiState(pages = pages, isFabVisible = false, isLastPage = false))
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.onboarding_btn_next))
            .assertDoesNotExist()
    }

    @Test
    fun fab_click_invokesOnFabClicked_whenOnLastPage() {
        var clicked = false
        setContent(
            uiState = OnboardingUiState(pages = pages, isFabVisible = true, isLastPage = true),
            onFabClicked = { clicked = true }
        )
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.onboarding_btn_start), useUnmergedTree = true)
            .performClick()
        assertTrue(clicked)
    }
}
