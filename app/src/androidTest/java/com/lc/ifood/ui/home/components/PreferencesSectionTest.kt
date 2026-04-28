package com.lc.ifood.ui.home.components

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lc.ifood.R
import com.lc.ifood.domain.model.MealType.BREAKFAST
import com.lc.ifood.domain.model.UserPreference
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PreferencesSectionTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    private val preferences = listOf(
        UserPreference(1, "Saudável", listOf(BREAKFAST))
    )

    private fun setContent(
        preferences: List<UserPreference> = this.preferences,
        onAddClick: () -> Unit = {}
    ) {
        composeRule.setContent {
            PreferencesSection(
                preferences = preferences,
                onAddClick = onAddClick
            )
        }
    }

    @Test
    fun preferencesSection_displaysTitle() {
        setContent()
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.home_preferences_title))
            .assertIsDisplayed()
    }

    @Test
    fun preferencesSection_displaysPreferenceLabel() {
        setContent()
        composeRule.onNodeWithText("Saudável").assertIsDisplayed()
    }

    @Test
    fun preferencesSection_addButtonIsVisible() {
        setContent()
        composeRule
            .onNodeWithContentDescription(
                composeRule.activity.getString(R.string.home_preferences_add_content_description)
            )
            .assertIsDisplayed()
    }

    @Test
    fun preferencesSection_addButtonFiresCallback() {
        var addClicked = false
        setContent(onAddClick = { addClicked = true })
        composeRule
            .onNodeWithContentDescription(
                composeRule.activity.getString(R.string.home_preferences_add_content_description)
            )
            .performClick()
        assertTrue(addClicked)
    }

    @Test
    fun preferencesSection_showsEmptyMessage_whenNoPreferences() {
        setContent(preferences = emptyList())
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.home_preferences_empty))
            .assertIsDisplayed()
    }
}
