package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.model.MealType.BREAKFAST
import com.lc.ifood.domain.repository.MealScheduleRepository
import com.lc.ifood.worker.MealRecommendationScheduler
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateMealScheduleUseCaseTest {

    private val repository: MealScheduleRepository = mockk()
    private val scheduler: MealRecommendationScheduler = mockk()
    private val useCase = UpdateMealScheduleUseCase(repository, scheduler)

    private val schedule = MealSchedule(BREAKFAST, 9, 30)

    @Test
    fun `invoke updates schedule in repository`() = runTest {
        coEvery { repository.updateMealSchedule(schedule) } just runs
        every { scheduler.schedule(schedule) } just runs

        useCase(schedule)

        coVerify { repository.updateMealSchedule(schedule) }
    }

    @Test
    fun `invoke schedules meal recommendation after updating`() = runTest {
        coEvery { repository.updateMealSchedule(schedule) } just runs
        every { scheduler.schedule(schedule) } just runs

        useCase(schedule)

        verify { scheduler.schedule(schedule) }
    }

    @Test
    fun `invoke calls repository before scheduler`() = runTest {
        val callOrder = mutableListOf<String>()
        coEvery { repository.updateMealSchedule(schedule) } coAnswers { callOrder.add("repository") }
        every { scheduler.schedule(schedule) } answers { callOrder.add("scheduler") }

        useCase(schedule)

        assert(callOrder == listOf("repository", "scheduler"))
    }
}
