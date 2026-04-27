package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.repository.OnboardingRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.runs
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CompleteOnboardingUseCaseTest {

    @MockK private lateinit var repository: OnboardingRepository
    private lateinit var useCase: CompleteOnboardingUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = CompleteOnboardingUseCase(repository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `invoke calls repository setOnboardingCompleted`() = runTest {
        coEvery { repository.setOnboardingCompleted() } just runs

        useCase()

        coVerify { repository.setOnboardingCompleted() }
    }
}
