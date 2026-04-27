package com.lc.ifood.ui.home

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lc.ifood.ui.home.components.HomeHeader
import com.lc.ifood.ui.home.components.MealRecommendationBottomSheet
import com.lc.ifood.ui.home.components.MealSchedulesSection
import com.lc.ifood.ui.home.components.PreferencesSection
import com.lc.ifood.ui.theme.IfoodBackground
import com.lc.ifood.ui.theme.IfoodRed

@Composable
fun HomeScreen(
    onEditSchedules: () -> Unit = {},
    onAddPreference: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {}
        LaunchedEffect(Unit) {
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    HomeContent(
        uiState = uiState,
        onEditSchedules = onEditSchedules,
        onAddPreference = onAddPreference,
        onSaveUserName = viewModel::saveUserName
    )

    uiState.mealRecommendation?.let { recommendation ->
        MealRecommendationBottomSheet(
            recommendation = recommendation,
            onDismiss = viewModel::dismissRecommendation
        )
    }
}

@Composable
internal fun HomeContent(
    uiState: HomeUiState,
    onEditSchedules: () -> Unit,
    onAddPreference: () -> Unit,
    onSaveUserName: (String) -> Unit
) {
    Scaffold(
        containerColor = IfoodRed
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
                .verticalScroll(rememberScrollState())
                .background(IfoodBackground)
                .padding(bottom = innerPadding.calculateBottomPadding())
        ) {
            HomeHeader(
                userName = uiState.userName,
                isUserLoaded = uiState.isUserLoaded,
                onSaveName = onSaveUserName
            )
            MealSchedulesSection(
                schedules = uiState.mealSchedules,
                onEditClick = onEditSchedules
            )
            PreferencesSection(
                preferences = uiState.preferences,
                onAddClick = onAddPreference
            )
            Spacer(Modifier.height(24.dp))
        }
    }
}
