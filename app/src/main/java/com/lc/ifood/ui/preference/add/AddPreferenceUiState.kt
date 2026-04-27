package com.lc.ifood.ui.preference.add

import com.lc.ifood.domain.model.MealType

/**
 * UI state for the Add Preference screen.
 *
 * @property label the preference name typed by the user.
 * @property mealTypeOptions all available [MealType] values shown as selectable chips.
 * @property selectedMealTypes the subset of [mealTypeOptions] the user has toggled on.
 * @property isSaving true while the save coroutine is running; disables the save button.
 * @property saved true once the save completes successfully; triggers navigation back.
 */
data class AddPreferenceUiState(
    val label: String = "",
    val mealTypeOptions: List<MealType> = emptyList(),
    val selectedMealTypes: Set<MealType> = emptySet(),
    val isSaving: Boolean = false,
    val saved: Boolean = false
) {
    /**
     * Whether the form is ready to be submitted.
     *
     * Both a non-blank label and at least one selected meal type are required. Computed
     * here in the state so the UI only needs to read this flag rather than repeat the logic.
     */
    val canSave: Boolean get() = label.isNotBlank() && selectedMealTypes.isNotEmpty()
}
