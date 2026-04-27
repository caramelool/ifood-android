package com.lc.ifood.ui.schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lc.ifood.R
import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.ui.composable.composable
import com.lc.ifood.ui.theme.IfoodBackground
import com.lc.ifood.ui.theme.IfoodRed
import com.lc.ifood.ui.theme.IfoodSurface
import com.lc.ifood.ui.theme.IfoodTextPrimary
import com.lc.ifood.ui.theme.IfoodTextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleAdjustmentScreen(
    onBack: () -> Unit,
    viewModel: ScheduleAdjustmentViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ScheduleAdjustmentContent(
        uiState = uiState,
        onUpdateTime = viewModel::updateTime,
        onSaveAll = { viewModel.saveAll(onBack) },
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ScheduleAdjustmentContent(
    uiState: ScheduleAdjustmentUiState,
    onUpdateTime: (MealSchedule, Int, Int) -> Unit,
    onSaveAll: () -> Unit,
    onBack: () -> Unit
) {
    var editingSchedule by remember { mutableStateOf<MealSchedule?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.schedule_adjustment_title),
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.common_back_content_description),
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = IfoodRed)
            )
        },
        containerColor = IfoodBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(Modifier.height(4.dp))

            uiState.schedules.forEach { schedule ->
                ScheduleCard(
                    schedule = schedule,
                    onEditClick = { editingSchedule = schedule }
                )
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = onSaveAll,
                enabled = !uiState.isSaving,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = IfoodRed),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (uiState.isSaving) stringResource(R.string.common_saving)
                           else stringResource(R.string.common_save),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }

    editingSchedule?.let { schedule ->
        TimePickerDialog(
            initialHour = schedule.hour,
            initialMinute = schedule.minute,
            onConfirm = { hour, minute ->
                onUpdateTime(schedule, hour, minute)
                editingSchedule = null
            },
            onDismiss = { editingSchedule = null }
        )
    }
}

@Composable
private fun ScheduleCard(
    schedule: MealSchedule,
    onEditClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = IfoodSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = schedule.mealType.composable().label,
                    fontSize = 14.sp,
                    color = IfoodTextSecondary
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = schedule.time,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = IfoodTextPrimary
                )
            }
            IconButton(onClick = onEditClick) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.schedule_adjustment_edit_content_description),
                    tint = IfoodRed
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimePickerDialog(
    initialHour: Int,
    initialMinute: Int,
    onConfirm: (Int, Int) -> Unit,
    onDismiss: () -> Unit
) {
    val timePickerState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = true
    )

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = IfoodSurface)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.schedule_adjustment_time_picker_title),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = IfoodTextPrimary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                TimePicker(
                    state = timePickerState,
                    colors = TimePickerDefaults.colors(
                        clockDialColor = IfoodBackground,
                        selectorColor = IfoodRed,
                        timeSelectorSelectedContainerColor = IfoodRed,
                        timeSelectorSelectedContentColor = Color.White
                    )
                )
                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(stringResource(R.string.common_cancel), color = IfoodTextSecondary)
                    }
                    TextButton(
                        onClick = { onConfirm(timePickerState.hour, timePickerState.minute) }
                    ) {
                        Text(
                            text = stringResource(R.string.common_ok),
                            color = IfoodRed,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
