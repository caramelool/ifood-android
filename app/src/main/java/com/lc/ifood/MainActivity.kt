package com.lc.ifood

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.lc.ifood.domain.model.MealRecommendation
import com.lc.ifood.ui.navigation.MainNavHost
import com.lc.ifood.ui.theme.IfoodTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IfoodTheme {
                MainNavHost()
            }
        }
    }

    companion object {
        const val EXTRA_MEAL_RECOMMENDATION = "extra_recommendation"
        fun intentRecommendation(context: Context, recommendation: MealRecommendation): Intent {
            return Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                putExtra(EXTRA_MEAL_RECOMMENDATION, recommendation)
            }
        }
    }
}
