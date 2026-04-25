package com.lc.ifood.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lc.ifood.R
import com.lc.ifood.domain.usecase.CompleteOnboardingUseCase
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

    init {
        inflatePages()
    }

    fun onPageChanged(currentPage: Int) {
        with(_uiState.value) {
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

    private fun inflatePages() {
        val pages = listOf(
            OnboardingPage(
                title = R.string.onboarding_title_1,
                subtitle = R.string.onboarding_subtitle_1,
                emoji = "🎉"
            ),
            OnboardingPage(
                title = R.string.onboarding_title_2,
                subtitle = R.string.onboarding_subtitle_2,
                emoji = "🕐"
            ),
            OnboardingPage(
                title = R.string.onboarding_title_3,
                subtitle = R.string.onboarding_subtitle_3,
                emoji = "🍽️"
            ),
            OnboardingPage(
                title = R.string.onboarding_title_4,
                subtitle = R.string.onboarding_subtitle_4,
                emoji = "🔔"
            )
        )
        _uiState.update { it.copy(pages = pages) }
    }
}
