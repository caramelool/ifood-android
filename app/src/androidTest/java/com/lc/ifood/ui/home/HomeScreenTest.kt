package com.lc.ifood.ui.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lc.ifood.R
import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.model.MealType.BREAKFAST
import com.lc.ifood.domain.model.MealType.LUNCH
import com.lc.ifood.domain.model.UserPreference
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    private val schedules = listOf(
        MealSchedule(BREAKFAST, 8, 0),
        MealSchedule(LUNCH, 12, 0)
    )
    private val preferences = listOf(
        UserPreference(1, "Saudável", listOf(BREAKFAST))
    )

    private fun setContent(
        uiState: HomeUiState = HomeUiState(),
        onEditSchedules: () -> Unit = {},
        onAddPreference: () -> Unit = {},
        onSaveUserName: (String) -> Unit = {}
    ) {
        composeRule.setContent {
            HomeContent(
                uiState = uiState,
                onEditSchedules = onEditSchedules,
                onAddPreference = onAddPreference,
                onSaveUserName = onSaveUserName
            )
        }
    }

    @Test
    fun schedulesSection_displaysTitle() {
        setContent(uiState = HomeUiState(mealSchedules = schedules, isUserLoaded = true))
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.home_schedules_title))
            .assertIsDisplayed()
    }

    @Test
    fun schedulesSection_displaysMealLabels() {
        setContent(uiState = HomeUiState(mealSchedules = schedules, isUserLoaded = true))
        composeRule.onNodeWithText("Café da Manhã").assertIsDisplayed()
        composeRule.onNodeWithText("Almoço").assertIsDisplayed()
    }

    @Test
    fun schedulesSection_editButtonIsVisible() {
        setContent(uiState = HomeUiState(mealSchedules = schedules, isUserLoaded = true))
        composeRule
            .onNodeWithContentDescription(
                composeRule.activity.getString(R.string.home_schedules_edit_content_description)
            )
            .assertIsDisplayed()
    }

    @Test
    fun schedulesSection_editButtonFiresCallback() {
        var editClicked = false
        setContent(
            uiState = HomeUiState(mealSchedules = schedules, isUserLoaded = true),
            onEditSchedules = { editClicked = true }
        )
        composeRule
            .onNodeWithContentDescription(
                composeRule.activity.getString(R.string.home_schedules_edit_content_description)
            )
            .performClick()
        assertTrue(editClicked)
    }

    @Test
    fun preferencesSection_displaysTitle() {
        setContent(uiState = HomeUiState(preferences = preferences, isUserLoaded = true))
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.home_preferences_title))
            .assertIsDisplayed()
    }

    @Test
    fun preferencesSection_displaysPreferenceLabel() {
        setContent(uiState = HomeUiState(preferences = preferences, isUserLoaded = true))
        composeRule.onNodeWithText("Saudável").assertIsDisplayed()
    }

    @Test
    fun preferencesSection_addButtonIsVisible() {
        setContent(uiState = HomeUiState(isUserLoaded = true))
        composeRule
            .onNodeWithContentDescription(
                composeRule.activity.getString(R.string.home_preferences_add_content_description)
            )
            .assertIsDisplayed()
    }

    @Test
    fun preferencesSection_addButtonFiresCallback() {
        var addClicked = false
        setContent(
            uiState = HomeUiState(isUserLoaded = true),
            onAddPreference = { addClicked = true }
        )
        composeRule
            .onNodeWithContentDescription(
                composeRule.activity.getString(R.string.home_preferences_add_content_description)
            )
            .performClick()
        assertTrue(addClicked)
    }

    @Test
    fun preferencesSection_showsEmptyMessage_whenNoPreferences() {
        setContent(uiState = HomeUiState(preferences = emptyList(), isUserLoaded = true))
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.home_preferences_empty))
            .assertIsDisplayed()
    }
}
