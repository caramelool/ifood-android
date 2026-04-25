package com.lc.ifood.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lc.ifood.ui.home.HomeScreen
import com.lc.ifood.ui.onboarding.OnboardingScreen
import com.lc.ifood.ui.preference.add.AddPreferenceScreen
import com.lc.ifood.ui.schedule.ScheduleAdjustmentScreen
import com.lc.ifood.ui.splash.SplashScreen

@Composable
fun MainNavHost() {
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