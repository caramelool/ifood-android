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
import com.lc.ifood.ui.home.HomeScreen
import com.lc.ifood.ui.navigation.AddPreferenceRoute
import com.lc.ifood.ui.navigation.HomeRoute
import com.lc.ifood.ui.navigation.OnboardingRoute
import com.lc.ifood.ui.navigation.ScheduleAdjustmentRoute
import com.lc.ifood.ui.navigation.SplashRoute
import com.lc.ifood.ui.onboarding.OnboardingScreen
import com.lc.ifood.ui.preference.AddPreferenceScreen
import com.lc.ifood.ui.schedule.ScheduleAdjustmentScreen
import com.lc.ifood.ui.splash.SplashScreen
import com.lc.ifood.ui.theme.IfoodTheme
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
