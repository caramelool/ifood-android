package com.lc.ifood.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lc.ifood.domain.model.MealRecommendation
import com.lc.ifood.ui.home.HomeScreen
import com.lc.ifood.ui.home.HomeViewModel
import com.lc.ifood.ui.onboarding.OnboardingScreen
import com.lc.ifood.ui.preference.add.AddPreferenceScreen
import com.lc.ifood.ui.schedule.ScheduleAdjustmentScreen
import com.lc.ifood.ui.splash.SplashScreen

@Composable
fun MainNavHost(pendingRecommendation: MutableState<MealRecommendation?>) {
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
            val homeViewModel: HomeViewModel = hiltViewModel()
            LaunchedEffect(pendingRecommendation) {
                pendingRecommendation.value?.let {
                    homeViewModel.showRecommendation(it)
                    pendingRecommendation.value = null
                }
            }
            HomeScreen(
                onEditSchedules = { navController.navigate(ScheduleAdjustmentRoute) },
                onAddPreference = { navController.navigate(AddPreferenceRoute) },
                viewModel = homeViewModel
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