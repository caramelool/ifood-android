package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.model.MealType
import com.lc.ifood.domain.model.MealType.BREAKFAST
import com.lc.ifood.domain.model.MealType.LUNCH
import com.lc.ifood.domain.repository.MealScheduleRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetMealsUseCaseTest {

    private val repository: MealScheduleRepository = mockk()
    private val useCase = GetMealsUseCase(repository)

    @Test
    fun `invoke maps meal schedules to meals`() = runTest {
        val schedules = listOf(
            MealSchedule(BREAKFAST, 8, 0),
            MealSchedule(LUNCH, 13, 0)
        )
        every { repository.getMealSchedules() } returns flowOf(schedules)

        assertEquals(listOf(BREAKFAST, LUNCH), useCase().first())
    }

    @Test
    fun `invoke returns empty list when schedules are empty`() = runTest {
        every { repository.getMealSchedules() } returns flowOf(emptyList())

        assertEquals(emptyList<MealType>(), useCase().first())
    }
}
