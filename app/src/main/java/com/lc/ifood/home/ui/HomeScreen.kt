package com.lc.ifood.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lc.ifood.core.ui.theme.IfoodBackground
import com.lc.ifood.core.ui.theme.IfoodRed
import com.lc.ifood.core.ui.theme.IfoodSurface
import com.lc.ifood.core.ui.theme.IfoodTextPrimary
import com.lc.ifood.core.ui.theme.IfoodTextSecondary

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
            HomeHeader()
            MealSchedulesSection(schedules = uiState.mealSchedules)
            PreferencesSection(preferences = uiState.preferences)
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun HomeHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 18.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Bem vindo,",
            fontSize = 16.sp,
            fontWeight = FontWeight.Light,
            color = IfoodTextPrimary
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = "Fulano",
            fontSize = 16.sp,
            fontWeight = FontWeight.Light,
            color = IfoodTextPrimary
        )
    }
}

@Composable
private fun MealSchedulesSection(schedules: List<MealSchedule>) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, bottom = 8.dp)
    ) {
        Text(
            text = "Seus Horários",
            modifier = Modifier.padding(horizontal = 16.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = IfoodTextPrimary
        )
        Spacer(Modifier.height(12.dp))
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(schedules) { schedule ->
                MealScheduleCard(schedule = schedule)
            }
        }
    }
}

@Composable
private fun MealScheduleCard(schedule: MealSchedule) {
    Column(
        modifier = Modifier.width(72.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = schedule.label,
            fontSize = 12.sp,
            color = IfoodTextSecondary,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(6.dp))
        Card(
            modifier = Modifier
                .width(72.dp)
                .height(64.dp),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = IfoodSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${schedule.hour}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = IfoodTextPrimary
                )
                Text(
                    text = schedule.period,
                    fontSize = 11.sp,
                    color = IfoodTextSecondary
                )
            }
        }
    }
}

@Composable
private fun PreferencesSection(preferences: List<UserPreference>) {
    Column(modifier = Modifier.padding(horizontal = 16.dp).padding(top = 24.dp, bottom = 8.dp)) {
        Text(
            text = "Suas Preferências",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = IfoodTextPrimary
        )
        Spacer(Modifier.height(12.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            preferences.forEach { preference ->
                PreferenceCard(preference = preference)
            }
        }
    }
}

@Composable
private fun PreferenceCard(preference: UserPreference) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = IfoodSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = preference.label,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = IfoodTextPrimary
            )
        }
    }
}
