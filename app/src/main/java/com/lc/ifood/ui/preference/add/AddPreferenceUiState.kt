package com.lc.ifood.ui.preference.add

import com.lc.ifood.domain.model.MealType

data class AddPreferenceUiState(
    val label: String = "",
    val mealTypeOptions: List<MealType> = emptyList(),
    val selectedMealTypes: Set<MealType> = emptySet(),
    val isSaving: Boolean = false,
    val saved: Boolean = false
) {
    val canSave: Boolean get() = label.isNotBlank() && selectedMealTypes.isNotEmpty()
}
