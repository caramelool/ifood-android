package com.lc.ifood.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lc.ifood.R
import com.lc.ifood.core.domain.model.MealType
import com.lc.ifood.core.ui.toLabel
import com.lc.ifood.core.ui.toShortLabel
import com.lc.ifood.core.ui.theme.IfoodBackground
import com.lc.ifood.core.ui.theme.IfoodRed
import com.lc.ifood.core.ui.theme.IfoodSurface
import com.lc.ifood.core.ui.theme.IfoodTextPrimary
import com.lc.ifood.core.ui.theme.IfoodTextSecondary

@Composable
fun HomeScreen(
    onEditSchedules: () -> Unit = {},
    onAddPreference: () -> Unit = {},
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
            text = stringResource(R.string.home_welcome),
            fontSize = 16.sp,
            fontWeight = FontWeight.Light,
            color = IfoodTextPrimary
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = stringResource(R.string.home_user_name),
            fontSize = 16.sp,
            fontWeight = FontWeight.Light,
            color = IfoodTextPrimary
        )
    }
}

@Composable
private fun MealSchedulesSection(
    schedules: List<MealSchedule>,
    onEditClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.home_schedules_title),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = IfoodTextPrimary
            )
            IconButton(onClick = onEditClick, modifier = Modifier.size(32.dp)) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.home_schedules_edit_content_description),
                    tint = IfoodRed,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
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
            text = schedule.mealType.toLabel(),
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
                    text = "%02d:%02d".format(schedule.hour, schedule.minute),
                    fontSize = 16.sp,
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
private fun PreferencesSection(
    preferences: List<UserPreference>,
    onAddClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 24.dp, bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.home_preferences_title),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = IfoodTextPrimary
            )
            Surface(
                onClick = onAddClick,
                shape = CircleShape,
                color = IfoodRed,
                modifier = Modifier.size(32.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.home_preferences_add_content_description),
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        if (preferences.isEmpty()) {
            Text(
                text = stringResource(R.string.home_preferences_empty),
                fontSize = 14.sp,
                color = IfoodTextSecondary,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                preferences.forEach { preference ->
                    PreferenceCard(preference = preference)
                }
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            Text(
                text = preference.label,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = IfoodTextPrimary
            )
            if (preference.mealTypes.isNotEmpty()) {
                Spacer(Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    preference.mealTypes.forEach { mealType ->
                        MealTypeChip(mealType = mealType)
                    }
                }
            }
        }
    }
}

@Composable
private fun MealTypeChip(mealType: MealType) {
    Surface(
        shape = RoundedCornerShape(50),
        color = IfoodRed.copy(alpha = 0.1f)
    ) {
        Text(
            text = mealType.toShortLabel(),
            fontSize = 11.sp,
            color = IfoodRed,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}
