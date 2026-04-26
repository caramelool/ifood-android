package com.lc.ifood.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lc.ifood.domain.usecase.GetMealSchedulesUseCase
import com.lc.ifood.domain.usecase.GetPreferencesUseCase
import com.lc.ifood.domain.usecase.GetUserUseCase
import com.lc.ifood.domain.usecase.SaveUserUseCase
import com.lc.ifood.domain.usecase.SeedDefaultSchedulesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMealSchedulesUseCase: GetMealSchedulesUseCase,
    private val getPreferencesUseCase: GetPreferencesUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val seedDefaultSchedulesUseCase: SeedDefaultSchedulesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            seedDefaultSchedulesUseCase()
        }
        observeData()
    }

    private fun observeData() {
        viewModelScope.launch {
            combine(
                getMealSchedulesUseCase(),
                getPreferencesUseCase(),
                getUserUseCase()
            ) { schedules, preferences, user ->
                HomeUiState(
                    mealSchedules = schedules,
                    preferences = preferences,
                    userName = user?.name,
                    isUserLoaded = true
                )
            }.collect { _uiState.value = it }
        }
    }

    fun saveUserName(name: String) {
        viewModelScope.launch {
            saveUserUseCase(name)
            _uiState.update {
                it.copy(
                    userName = name,
                    isUserLoaded = true
                )
            }
        }
    }
}
