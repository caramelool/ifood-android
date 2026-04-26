package com.lc.ifood.ui.schedule

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lc.ifood.R
import com.lc.ifood.domain.model.Meal
import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.model.MealType
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScheduleAdjustmentScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    private val breakfast = Meal(MealType.BREAKFAST, "Café da Manhã", "Café")
    private val lunch = Meal(MealType.LUNCH, "Almoço", "Almoço")
    private val schedules = listOf(
        MealSchedule(breakfast, 8, 0),
        MealSchedule(lunch, 12, 30)
    )

    private fun setContent(uiState: ScheduleAdjustmentUiState) {
        composeRule.setContent {
            ScheduleAdjustmentContent(
                uiState = uiState,
                onUpdateTime = { _, _, _ -> },
                onSaveAll = {},
                onBack = {}
            )
        }
    }

    @Test
    fun scheduleCards_displayMealLabels() {
        setContent(ScheduleAdjustmentUiState(schedules = schedules))
        composeRule.onNodeWithText("Café da Manhã").assertIsDisplayed()
        composeRule.onNodeWithText("Almoço").assertIsDisplayed()
    }

    @Test
    fun scheduleCards_displayFormattedTimes() {
        setContent(ScheduleAdjustmentUiState(schedules = schedules))
        composeRule.onNodeWithText("08:00").assertIsDisplayed()
        composeRule.onNodeWithText("12:30").assertIsDisplayed()
    }

    @Test
    fun saveButton_isEnabled_whenNotSaving() {
        setContent(ScheduleAdjustmentUiState(schedules = schedules, isSaving = false))
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.common_save))
            .assertIsEnabled()
    }

    @Test
    fun saveButton_isDisabled_whenSaving() {
        setContent(ScheduleAdjustmentUiState(schedules = schedules, isSaving = true))
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.common_saving))
            .assertIsNotEnabled()
    }

    @Test
    fun editIcon_clickOpensTimePicker() {
        setContent(ScheduleAdjustmentUiState(schedules = schedules))
        composeRule
            .onNodeWithContentDescription(
                composeRule.activity.getString(R.string.schedule_adjustment_edit_content_description)
            )
            .performClick()
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.schedule_adjustment_time_picker_title))
            .assertIsDisplayed()
    }
}
