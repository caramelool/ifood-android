package com.lc.ifood.domain.usecase

import app.cash.turbine.test
import com.lc.ifood.domain.repository.OnboardingRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetOnboardingStatusUseCaseTest {

    private val repository: OnboardingRepository = mockk()
    private val useCase = GetOnboardingStatusUseCase(repository)

    @Test
    fun `invoke returns true when onboarding is completed`() = runTest {
        every { repository.isOnboardingCompleted } returns flowOf(true)

        useCase().test {
            assertTrue(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `invoke returns false when onboarding is not completed`() = runTest {
        every { repository.isOnboardingCompleted } returns flowOf(false)

        useCase().test {
            assertFalse(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
