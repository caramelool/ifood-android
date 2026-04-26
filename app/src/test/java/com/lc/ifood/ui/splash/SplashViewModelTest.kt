package com.lc.ifood.ui.splash

import app.cash.turbine.test
import com.lc.ifood.domain.usecase.GetOnboardingStatusUseCase
import com.lc.ifood.util.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    private val getOnboardingStatus: GetOnboardingStatusUseCase = mockk()

    private fun createViewModel() = SplashViewModel(getOnboardingStatus)

    @Test
    fun `destination emits Loading then Home when onboarding is completed`() = runTest(testDispatcher) {
        every { getOnboardingStatus.invoke() } returns flowOf(true)
        val vm = createViewModel()
        vm.destination.test {
            assertEquals(SplashDestination.Loading, awaitItem())
            assertEquals(SplashDestination.Home, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `destination emits Loading then Onboarding when onboarding is not completed`() = runTest(testDispatcher) {
        every { getOnboardingStatus.invoke() } returns flowOf(false)
        val vm = createViewModel()
        vm.destination.test {
            assertEquals(SplashDestination.Loading, awaitItem())
            assertEquals(SplashDestination.Onboarding, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
