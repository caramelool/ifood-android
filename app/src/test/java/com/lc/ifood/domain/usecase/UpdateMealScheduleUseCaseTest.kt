package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.model.MealType.BREAKFAST
import com.lc.ifood.domain.repository.MealScheduleRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateMealScheduleUseCaseTest {

    @MockK private lateinit var repository: MealScheduleRepository
    private lateinit var useCase: UpdateMealScheduleUseCase

    private val schedule = MealSchedule(BREAKFAST, 9, 30)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = UpdateMealScheduleUseCase(repository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `invoke updates schedule in repository`() = runTest {
        coEvery { repository.updateMealSchedule(schedule) } just runs

        useCase(schedule)

        coVerify { repository.updateMealSchedule(schedule) }
    }
}
