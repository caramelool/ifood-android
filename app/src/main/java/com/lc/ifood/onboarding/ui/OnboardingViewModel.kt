package com.lc.ifood.onboarding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lc.ifood.onboarding.domain.CompleteOnboardingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val completeOnboardingUseCase: CompleteOnboardingUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    private var fabJob: Job? = null

    fun onPageChanged(currentPage: Int) {
        with(_uiState.value) {
            if (isOnboardCompleted) {
                return
            }
            val isLastPage = currentPage == pages.size - 1
            fabJob?.cancel()
            if (isLastPage) {
                _uiState.update {
                    it.copy(
                        isFabVisible = true,
                        isOnboardCompleted = true
                    )
                }
            } else {
                _uiState.update { it.copy(isFabVisible = false) }
                fabJob = viewModelScope.launch {
                    delay(1.5.seconds)
                    _uiState.update { it.copy(isFabVisible = true) }
                }
            }
        }
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            completeOnboardingUseCase()
        }
    }
}
