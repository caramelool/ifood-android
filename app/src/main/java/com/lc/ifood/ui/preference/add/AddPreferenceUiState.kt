package com.lc.ifood.ui.preference.add

import com.lc.ifood.domain.model.Meal

data class AddPreferenceUiState(
    val label: String = "",
    val mealOptions: List<Meal> = emptyList(),
    val selectedMeals: Set<Meal> = emptySet(),
    val isSaving: Boolean = false,
    val saved: Boolean = false
) {
    val canSave: Boolean get() = label.isNotBlank() && selectedMeals.isNotEmpty()
}
