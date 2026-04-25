package com.lc.ifood.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lc.ifood.home.domain.GetMealSchedulesUseCase
import com.lc.ifood.home.domain.GetPreferencesUseCase
import com.lc.ifood.home.data.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMealSchedulesUseCase: GetMealSchedulesUseCase,
    private val getPreferencesUseCase: GetPreferencesUseCase,
    private val scheduleRepository: ScheduleRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            scheduleRepository.seedDefaultsIfEmpty()
        }
        observeData()
    }

    private fun observeData() {
        viewModelScope.launch {
            combine(
                getMealSchedulesUseCase(),
                getPreferencesUseCase()
            ) { schedules, preferences ->
                HomeUiState(mealSchedules = schedules, preferences = preferences)
            }.collect { _uiState.value = it }
        }
    }
}
