package com.lc.ifood.ui.home.components

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
class HomeHeaderTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    private fun setContent(
        userName: String? = null,
        isUserLoaded: Boolean = false,
        onSaveName: (String) -> Unit = {}
    ) {
        composeRule.setContent {
            HomeHeader(
                userName = userName,
                isUserLoaded = isUserLoaded,
                onSaveName = onSaveName
            )
        }
    }

    @Test
    fun welcomeText_isDisplayed() {
        setContent()
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.home_welcome))
            .assertIsDisplayed()
    }

    @Test
    fun userName_isDisplayed_whenNotNull() {
        setContent(userName = "Lucas", isUserLoaded = true)
        composeRule.onNodeWithText("Lucas").assertIsDisplayed()
    }

    @Test
    fun dialog_isShown_whenIsUserLoadedAndUserNameIsNull() {
        setContent(userName = null, isUserLoaded = true)
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.home_username_dialog_title))
            .assertIsDisplayed()
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.home_username_dialog_message))
            .assertIsDisplayed()
    }

    @Test
    fun dialog_isNotShown_whenUserNameIsPresent() {
        setContent(userName = "Lucas", isUserLoaded = true)
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.home_username_dialog_title))
            .assertDoesNotExist()
    }

    @Test
    fun dialog_isNotShown_whenUserNotLoaded() {
        setContent(userName = null, isUserLoaded = false)
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.home_username_dialog_title))
            .assertDoesNotExist()
    }

    @Test
    fun dialog_confirm_callsOnSaveName_withTrimmedName() {
        var savedName = ""
        setContent(userName = null, isUserLoaded = true, onSaveName = { savedName = it })
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.home_username_dialog_hint))
            .performTextInput("  Ana  ")
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.home_username_dialog_confirm))
            .performClick()
        assertTrue(savedName == "Ana")
    }

    @Test
    fun dialog_confirm_doesNotCallOnSaveName_whenBlank() {
        var callCount = 0
        setContent(userName = null, isUserLoaded = true, onSaveName = { callCount++ })
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.home_username_dialog_confirm))
            .performClick()
        assertTrue(callCount == 0)
    }

    @Test
    fun dialog_dismissesAfterConfirm() {
        setContent(userName = null, isUserLoaded = true)
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.home_username_dialog_hint))
            .performTextInput("Ana")
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.home_username_dialog_confirm))
            .performClick()
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.home_username_dialog_title))
            .assertDoesNotExist()
    }
}
