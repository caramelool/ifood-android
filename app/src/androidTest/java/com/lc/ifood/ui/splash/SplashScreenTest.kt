package com.lc.ifood.ui.splash

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
class SplashScreenTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    private fun setContent(
        showPermissionError: Boolean = false,
        onPermissionErrorConfirm: () -> Unit = {}
    ) {
        composeRule.setContent {
            SplashContent(
                showPermissionError = showPermissionError,
                onPermissionErrorConfirm = onPermissionErrorConfirm
            )
        }
    }

    @Test
    fun content_showsNothing_whenPermissionErrorIsFalse() {
        setContent(showPermissionError = false)
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.splash_notification_required_title))
            .assertDoesNotExist()
    }

    @Test
    fun content_showsDialog_whenPermissionErrorIsTrue() {
        setContent(showPermissionError = true)
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.splash_notification_required_title))
            .assertIsDisplayed()
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.splash_notification_required_message))
            .assertIsDisplayed()
    }

    @Test
    fun content_dialogConfirm_callsOnPermissionErrorConfirm() {
        var confirmed = false
        setContent(showPermissionError = true, onPermissionErrorConfirm = { confirmed = true })
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.splash_notification_required_confirm))
            .performClick()
        assertTrue(confirmed)
    }
}
