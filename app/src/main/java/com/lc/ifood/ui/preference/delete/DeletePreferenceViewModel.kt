package com.lc.ifood.ui.preference.delete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lc.ifood.domain.usecase.DeletePreferenceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeletePreferenceViewModel @Inject constructor(
    private val deletePreferenceUseCase: DeletePreferenceUseCase
) : ViewModel() {

    fun delete(id: Int) {
        viewModelScope.launch { deletePreferenceUseCase(id) }
    }
}
