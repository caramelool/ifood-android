package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.model.MealType
import com.lc.ifood.domain.model.MealType.BREAKFAST
import com.lc.ifood.domain.model.MealType.LUNCH
import com.lc.ifood.domain.repository.MealScheduleRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.every
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetMealsUseCaseTest {

    @MockK private lateinit var repository: MealScheduleRepository
    private lateinit var useCase: GetMealsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetMealsUseCase(repository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

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
