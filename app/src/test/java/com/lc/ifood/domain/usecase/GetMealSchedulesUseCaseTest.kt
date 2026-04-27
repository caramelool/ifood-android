package com.lc.ifood.domain.usecase

import app.cash.turbine.test
import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.model.MealType.BREAKFAST
import com.lc.ifood.domain.repository.MealScheduleRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetMealSchedulesUseCaseTest {

    private val repository: MealScheduleRepository = mockk()
    private val useCase = GetMealSchedulesUseCase(repository)

    private val schedule = MealSchedule(BREAKFAST, 8, 0)

    @Test
    fun `invoke returns flow of meal schedules from repository`() = runTest {
        every { repository.getMealSchedules() } returns flowOf(listOf(schedule))

        useCase().test {
            assertEquals(listOf(schedule), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `invoke returns empty list when repository emits empty`() = runTest {
        every { repository.getMealSchedules() } returns flowOf(emptyList())

        useCase().test {
            assertEquals(emptyList<MealSchedule>(), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
