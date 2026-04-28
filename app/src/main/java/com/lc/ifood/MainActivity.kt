package com.lc.ifood

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.IntentCompat
import com.lc.ifood.domain.model.MealRecommendation
import com.lc.ifood.ui.navigation.MainNavHost
import com.lc.ifood.ui.theme.IfoodTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val pendingRecommendation = mutableStateOf<MealRecommendation?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        pendingRecommendation.value = IntentCompat.getParcelableExtra(
            intent, EXTRA_MEAL_RECOMMENDATION, MealRecommendation::class.java
        )
        setContent {
            IfoodTheme {
                MainNavHost(pendingRecommendation = pendingRecommendation)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        pendingRecommendation.value = IntentCompat.getParcelableExtra(
            intent, EXTRA_MEAL_RECOMMENDATION, MealRecommendation::class.java
        )
    }

    companion object {
        const val EXTRA_MEAL_RECOMMENDATION = "extra_recommendation"
        fun intentRecommendation(context: Context, recommendation: MealRecommendation): Intent {
            return Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_SINGLE_TOP
                putExtra(EXTRA_MEAL_RECOMMENDATION, recommendation)
            }
        }
    }
}
