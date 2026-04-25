package com.lc.ifood.preference.ui

import com.lc.ifood.core.domain.model.MealType

data class AddPreferenceUiState(
    val label: String = "",
    val selectedMealTypes: Set<MealType> = emptySet(),
    val isSaving: Boolean = false,
    val saved: Boolean = false
) {
    val canSave: Boolean get() = label.isNotBlank() && selectedMealTypes.isNotEmpty()
}
