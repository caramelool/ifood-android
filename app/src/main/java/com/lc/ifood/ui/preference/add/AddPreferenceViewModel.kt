package com.lc.ifood.ui.preference.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lc.ifood.domain.model.MealType
import com.lc.ifood.domain.usecase.GetMealsBySchedulesUseCase
import com.lc.ifood.domain.usecase.SavePreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPreferenceViewModel @Inject constructor(
    private val getMeals: GetMealsBySchedulesUseCase,
    private val savePreference: SavePreferenceUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddPreferenceUiState())
    val uiState: StateFlow<AddPreferenceUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getMeals().collect { options ->
                _uiState.update {
                    it.copy(mealOptions = options)
                }
            }
        }
    }

    fun onLabelChange(label: String) {
        _uiState.value = _uiState.value.copy(label = label)
    }

    fun toggleMealType(mealType: MealType) {
        val current = _uiState.value.selectedMealTypes.toMutableSet()
        if (mealType in current) current.remove(mealType) else current.add(mealType)
        _uiState.value = _uiState.value.copy(selectedMealTypes = current)
    }

    fun save(onDone: () -> Unit) {
        val state = _uiState.value
        if (!state.canSave) return
        viewModelScope.launch {
            _uiState.value = state.copy(isSaving = true)
            savePreference(
                label = state.label.trim(),
                mealTypes = state.selectedMealTypes.toList()
            )
            _uiState.value = _uiState.value.copy(isSaving = false, saved = true)
            onDone()
        }
    }
}
