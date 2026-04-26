package com.lc.ifood.domain.usecase

import app.cash.turbine.test
import com.lc.ifood.domain.model.Meal
import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.model.MealType
import com.lc.ifood.domain.repository.MealScheduleRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetMealsUseCaseTest {

    private val repository: MealScheduleRepository = mockk()
    private val useCase = GetMealsUseCase(repository)

    private val breakfast = Meal(MealType.BREAKFAST, "Café da Manhã", "Café")
    private val lunch = Meal(MealType.LUNCH, "Almoço", "Almoço")

    @Test
    fun `invoke maps meal schedules to meals`() = runTest {
        val schedules = listOf(
            MealSchedule(breakfast, 8, 0),
            MealSchedule(lunch, 13, 0)
        )
        every { repository.getMealSchedules() } returns flowOf(schedules)

        useCase().test {
            assertEquals(listOf(breakfast, lunch), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `invoke returns empty list when schedules are empty`() = runTest {
        every { repository.getMealSchedules() } returns flowOf(emptyList())

        useCase().test {
            assertEquals(emptyList<Meal>(), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
