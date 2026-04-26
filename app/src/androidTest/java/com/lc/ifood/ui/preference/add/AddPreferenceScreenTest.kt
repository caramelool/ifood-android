package com.lc.ifood.ui.preference.add

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lc.ifood.R
import com.lc.ifood.domain.model.Meal
import com.lc.ifood.domain.model.MealType
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddPreferenceScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    private val breakfast = Meal(MealType.BREAKFAST, "Café da Manhã", "Café")
    private val lunch = Meal(MealType.LUNCH, "Almoço", "Almoço")

    private fun setContent(
        uiState: AddPreferenceUiState = AddPreferenceUiState(),
        onLabelChange: (String) -> Unit = {},
        onToggleMeal: (Meal) -> Unit = {},
        onSave: () -> Unit = {}
    ) {
        composeRule.setContent {
            AddPreferenceContent(
                uiState = uiState,
                onLabelChange = onLabelChange,
                onToggleMeal = onToggleMeal,
                onSave = onSave,
                onBack = {}
            )
        }
    }

    @Test
    fun labelField_isDisplayed() {
        setContent()
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.add_preference_label_field))
            .assertIsDisplayed()
    }

    @Test
    fun mealOptions_areDisplayed() {
        setContent(uiState = AddPreferenceUiState(mealOptions = listOf(breakfast, lunch)))
        composeRule.onNodeWithText("Café da Manhã").assertIsDisplayed()
        composeRule.onNodeWithText("Almoço").assertIsDisplayed()
    }

    @Test
    fun saveButton_isDisabled_whenCanSaveIsFalse() {
        setContent(
            uiState = AddPreferenceUiState(
                label = "",
                mealOptions = listOf(breakfast),
                selectedMeals = emptySet()
            )
        )
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.common_save))
            .assertIsNotEnabled()
    }

    @Test
    fun saveButton_isEnabled_whenCanSaveIsTrue() {
        setContent(
            uiState = AddPreferenceUiState(
                label = "Saudável",
                mealOptions = listOf(breakfast),
                selectedMeals = setOf(breakfast)
            )
        )
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.common_save))
            .assertIsEnabled()
    }

    @Test
    fun saveButton_showsSavingText_whenIsSaving() {
        setContent(
            uiState = AddPreferenceUiState(
                label = "Saudável",
                selectedMeals = setOf(breakfast),
                isSaving = true
            )
        )
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.common_saving))
            .assertIsDisplayed()
    }

    @Test
    fun saveButton_isDisabled_whenIsSaving() {
        setContent(
            uiState = AddPreferenceUiState(
                label = "Saudável",
                selectedMeals = setOf(breakfast),
                isSaving = true
            )
        )
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.common_saving))
            .assertIsNotEnabled()
    }

    @Test
    fun labelField_callsOnLabelChange_onInput() {
        var changedTo = ""
        setContent(onLabelChange = { changedTo = it })
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.add_preference_label_field))
            .performTextInput("Vegano")
        assert(changedTo == "Vegano")
    }
}
