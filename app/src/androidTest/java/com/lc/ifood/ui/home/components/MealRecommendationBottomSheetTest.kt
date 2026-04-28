package com.lc.ifood.ui.home.components

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertDoesNotExist
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lc.ifood.R
import com.lc.ifood.domain.model.MealRecommendation
import com.lc.ifood.domain.model.MealType
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MealRecommendationBottomSheetTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>()

    private val sampleRecommendation = MealRecommendation(
        mealType = MealType.BREAKFAST,
        placeName = "Padaria Central",
        placeAddress = "Rua das Flores, 123",
        mealName = "Pão na Chapa",
        mealDescription = "Pão de forma tostado com manteiga",
        mealPrice = 12.50,
        preferences = listOf("Saudável", "Econômico"),
        mealImageUrl = ""
    )

    private val noPreferencesRecommendation = MealRecommendation(
        mealType = MealType.LUNCH,
        placeName = "Restaurante Bom Sabor",
        placeAddress = "Av. Brasil, 456",
        mealName = "Frango Grelhado",
        mealDescription = "Peito de frango com arroz e feijão",
        mealPrice = 29.90,
        preferences = emptyList(),
        mealImageUrl = ""
    )

    private fun setContent(
        recommendation: MealRecommendation = sampleRecommendation,
        onDismiss: () -> Unit = {}
    ) {
        composeRule.setContent {
            MealRecommendationBottomSheet(
                recommendation = recommendation,
                onDismiss = onDismiss
            )
        }
    }

    @Test
    fun bottomSheet_displaysMealName() {
        setContent()
        composeRule.onNodeWithText("Pão na Chapa").assertIsDisplayed()
    }

    @Test
    fun bottomSheet_displaysMealDescription() {
        setContent()
        composeRule.onNodeWithText("Pão de forma tostado com manteiga").assertIsDisplayed()
    }

    @Test
    fun bottomSheet_displaysPlaceNameAndAddress() {
        setContent()
        composeRule
            .onNodeWithText("Padaria Central · Rua das Flores, 123")
            .assertIsDisplayed()
    }

    @Test
    fun bottomSheet_displaysMealTypeBadge_breakfast() {
        setContent()
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.meal_type_breakfast))
            .assertIsDisplayed()
    }

    @Test
    fun bottomSheet_displaysMealTypeBadge_lunch() {
        setContent(recommendation = noPreferencesRecommendation)
        composeRule
            .onNodeWithText(composeRule.activity.getString(R.string.meal_type_lunch))
            .assertIsDisplayed()
    }

    @Test
    fun bottomSheet_displaysFormattedPrice() {
        setContent()
        composeRule
            .onNodeWithText("R$ %.2f".format(sampleRecommendation.mealPrice))
            .assertIsDisplayed()
    }

    @Test
    fun bottomSheet_displaysPreferenceTags_whenNotEmpty() {
        setContent()
        composeRule.onNodeWithText("Saudável").assertIsDisplayed()
        composeRule.onNodeWithText("Econômico").assertIsDisplayed()
    }

    @Test
    fun bottomSheet_doesNotDisplayPreferenceTags_whenEmpty() {
        setContent(recommendation = noPreferencesRecommendation)
        composeRule.onNodeWithText("Saudável").assertDoesNotExist()
        composeRule.onNodeWithText("Econômico").assertDoesNotExist()
    }

    @Test
    fun bottomSheet_displaysBuyButton() {
        setContent()
        composeRule.onNodeWithText("Comprar").assertIsDisplayed()
    }

    @Test
    fun bottomSheet_buyButton_callsOnDismiss() {
        var dismissed = false
        setContent(onDismiss = { dismissed = true })
        composeRule.onNodeWithText("Comprar").performClick()
        assertTrue(dismissed)
    }
}
