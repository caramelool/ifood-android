package com.lc.ifood.ui.home.components

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
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MealSchedulesSectionTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    private val schedules = listOf(
        MealSchedule(BREAKFAST, 8, 0),
        MealSchedule(LUNCH, 12, 30)
    )

    private fun setContent(
        schedules: List<MealSchedule> = this.schedules,
        onEditClick: () -> Unit = {}
    ) {
        composeRule.setContent {
            MealSchedulesSection(
                schedules = schedules,
                onEditClick = onEditClick
            )
        }
    }

    @Test
    fun schedulesSection_displaysTitle() {
        setContent()
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.home_schedules_title))
            .assertIsDisplayed()
    }

    @Test
    fun schedulesSection_displaysMealLabels() {
        setContent()
        composeRule.onNodeWithText("Café da Manhã").assertIsDisplayed()
        composeRule.onNodeWithText("Almoço").assertIsDisplayed()
    }

    @Test
    fun schedulesSection_editButtonIsVisible() {
        setContent()
        composeRule
            .onNodeWithContentDescription(
                composeRule.activity.getString(R.string.home_schedules_edit_content_description)
            )
            .assertIsDisplayed()
    }

    @Test
    fun schedulesSection_editButtonFiresCallback() {
        var editClicked = false
        setContent(onEditClick = { editClicked = true })
        composeRule
            .onNodeWithContentDescription(
                composeRule.activity.getString(R.string.home_schedules_edit_content_description)
            )
            .performClick()
        assertTrue(editClicked)
    }

    @Test
    fun schedulesSection_displaysFormattedTime() {
        setContent()
        composeRule.onNodeWithText("08:00").assertIsDisplayed()
        composeRule.onNodeWithText("12:30").assertIsDisplayed()
    }
}
