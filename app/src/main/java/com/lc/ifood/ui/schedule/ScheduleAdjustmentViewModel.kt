package com.lc.ifood.ui.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.usecase.GetMealSchedulesUseCase
import com.lc.ifood.domain.usecase.UpdateMealScheduleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleAdjustmentViewModel @Inject constructor(
    private val getMealSchedulesUseCase: GetMealSchedulesUseCase,
    private val updateMealScheduleUseCase: UpdateMealScheduleUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScheduleAdjustmentUiState())
    val uiState: StateFlow<ScheduleAdjustmentUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val schedules = getMealSchedulesUseCase().first()
            _uiState.value = _uiState.value.copy(schedules = schedules)
        }
    }

    fun updateTime(schedule: MealSchedule, hour: Int, minute: Int) {
        val updated = schedule.copy(
            hour = hour,
            minute = minute
        )
        _uiState.value = _uiState.value.copy(
            schedules = _uiState.value.schedules.map {
                if (it.meal.type == schedule.meal.type) updated else it
            }
        )
    }

    fun saveAll(onDone: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSaving = true)
            _uiState.value.schedules.forEach { updateMealScheduleUseCase(it) }
            _uiState.value = _uiState.value.copy(isSaving = false, saved = true)
            onDone()
        }
    }
}
