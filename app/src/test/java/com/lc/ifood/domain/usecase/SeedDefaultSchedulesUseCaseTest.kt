package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.repository.MealScheduleRepository
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
class SeedDefaultSchedulesUseCaseTest {

    @MockK private lateinit var repository: MealScheduleRepository
    private lateinit var useCase: SeedDefaultSchedulesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = SeedDefaultSchedulesUseCase(repository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `invoke delegates to repository seedDefaultsIfEmpty`() = runTest {
        coEvery { repository.seedDefaultsIfEmpty() } just runs

        useCase()

        coVerify { repository.seedDefaultsIfEmpty() }
    }
}
