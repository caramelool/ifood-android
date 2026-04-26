package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.repository.OnboardingRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CompleteOnboardingUseCaseTest {

    private val repository: OnboardingRepository = mockk()
    private val useCase = CompleteOnboardingUseCase(repository)

    @Test
    fun `invoke calls repository setOnboardingCompleted`() = runTest {
        coEvery { repository.setOnboardingCompleted() } just runs

        useCase()

        coVerify { repository.setOnboardingCompleted() }
    }
}
