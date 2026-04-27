package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.repository.OnboardingRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetOnboardingStatusUseCaseTest {

    @MockK private lateinit var repository: OnboardingRepository
    private lateinit var useCase: GetOnboardingStatusUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetOnboardingStatusUseCase(repository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `invoke returns true when onboarding is completed`() = runTest {
        every { repository.isOnboardingCompleted } returns flowOf(true)

        assertTrue(useCase().first())
    }

    @Test
    fun `invoke returns false when onboarding is not completed`() = runTest {
        every { repository.isOnboardingCompleted } returns flowOf(false)

        assertFalse(useCase().first())
    }
}
