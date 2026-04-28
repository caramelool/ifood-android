package com.lc.ifood.ui.schedule

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lc.ifood.R
import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.model.MealType.BREAKFAST
import com.lc.ifood.domain.model.MealType.LUNCH
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScheduleAdjustmentScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    private val schedules = listOf(
        MealSchedule(BREAKFAST, 8, 0),
        MealSchedule(LUNCH, 12, 30)
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
            .onAllNodesWithContentDescription(
                composeRule.activity.getString(R.string.schedule_adjustment_edit_content_description)
            )
            .onFirst()
            .performClick()
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.schedule_adjustment_time_picker_title))
            .assertIsDisplayed()
    }
}
