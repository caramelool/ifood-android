package com.lc.ifood.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lc.ifood.domain.repository.ScheduleRepository
import com.lc.ifood.domain.usecase.GetMealSchedulesUseCase
import com.lc.ifood.domain.usecase.GetPreferencesUseCase
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
