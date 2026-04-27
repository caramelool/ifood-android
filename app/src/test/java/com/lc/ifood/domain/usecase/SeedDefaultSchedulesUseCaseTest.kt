package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.repository.MealScheduleRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SeedDefaultSchedulesUseCaseTest {

    private val repository: MealScheduleRepository = mockk()
    private val useCase = SeedDefaultSchedulesUseCase(repository)

    @Test
    fun `invoke delegates to repository seedDefaultsIfEmpty`() = runTest {
        coEvery { repository.seedDefaultsIfEmpty() } just runs

        useCase()

        coVerify { repository.seedDefaultsIfEmpty() }
    }
}
