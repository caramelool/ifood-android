package com.lc.ifood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lc.ifood.core.navigation.AddPreferenceRoute
import com.lc.ifood.core.navigation.HomeRoute
import com.lc.ifood.core.navigation.OnboardingRoute
import com.lc.ifood.core.navigation.ScheduleAdjustmentRoute
import com.lc.ifood.core.navigation.SplashRoute
import com.lc.ifood.core.ui.theme.IfoodTheme
import com.lc.ifood.home.ui.HomeScreen
import com.lc.ifood.onboarding.ui.OnboardingScreen
import com.lc.ifood.preference.ui.AddPreferenceScreen
import com.lc.ifood.schedule.ui.ScheduleAdjustmentScreen
import com.lc.ifood.splash.ui.SplashScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IfoodTheme {
                IfoodNavHost()
            }
        }
    }
}

@Composable
fun IfoodNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = SplashRoute,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable<SplashRoute> {
            SplashScreen(
                onNavigateToOnboarding = {
                    navController.navigate(OnboardingRoute) {
                        popUpTo(SplashRoute) { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(HomeRoute) {
                        popUpTo(SplashRoute) { inclusive = true }
                    }
                }
            )
        }

        composable<OnboardingRoute> {
            OnboardingScreen(
                onOnboardingComplete = {
                    navController.navigate(HomeRoute) {
                        popUpTo(OnboardingRoute) { inclusive = true }
                    }
                }
            )
        }

        composable<HomeRoute> {
            HomeScreen(
                onEditSchedules = { navController.navigate(ScheduleAdjustmentRoute) },
                onAddPreference = { navController.navigate(AddPreferenceRoute) }
            )
        }

        composable<ScheduleAdjustmentRoute> {
            ScheduleAdjustmentScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable<AddPreferenceRoute> {
            AddPreferenceScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
