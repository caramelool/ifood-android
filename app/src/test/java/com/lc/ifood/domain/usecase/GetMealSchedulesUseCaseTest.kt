package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.model.MealType.BREAKFAST
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
class GetMealSchedulesUseCaseTest {

    @MockK private lateinit var repository: MealScheduleRepository
    private lateinit var useCase: GetMealSchedulesUseCase

    private val schedule = MealSchedule(BREAKFAST, 8, 0)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetMealSchedulesUseCase(repository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `invoke returns flow of meal schedules from repository`() = runTest {
        every { repository.getMealSchedules() } returns flowOf(listOf(schedule))

        assertEquals(listOf(schedule), useCase().first())
    }

    @Test
    fun `invoke returns empty list when repository emits empty`() = runTest {
        every { repository.getMealSchedules() } returns flowOf(emptyList())

        assertEquals(emptyList<MealSchedule>(), useCase().first())
    }
}
