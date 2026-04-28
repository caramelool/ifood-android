package com.lc.ifood.ui.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lc.ifood.R
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

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
    fun header_displaysWelcomeText() {
        setContent(uiState = HomeUiState(isUserLoaded = true, userName = "Lucas"))
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.home_welcome))
            .assertIsDisplayed()
    }

    @Test
    fun header_displaysUserName_whenProvided() {
        setContent(uiState = HomeUiState(isUserLoaded = true, userName = "Lucas"))
        composeRule.onNodeWithText("Lucas").assertIsDisplayed()
    }

    @Test
    fun header_showsDialog_whenUserLoadedAndNameIsNull() {
        setContent(uiState = HomeUiState(isUserLoaded = true, userName = null))
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.home_username_dialog_title))
            .assertIsDisplayed()
    }

    @Test
    fun header_dialogConfirm_callsOnSaveUserName() {
        var savedName = ""
        setContent(
            uiState = HomeUiState(isUserLoaded = true, userName = null),
            onSaveUserName = { savedName = it }
        )
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.home_username_dialog_hint))
            .performTextInput("Joao")
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.home_username_dialog_confirm))
            .performClick()
        assertTrue(savedName == "Joao")
    }
}
