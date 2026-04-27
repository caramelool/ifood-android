package com.lc.ifood.ui.schedule

import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.model.MealType.BREAKFAST
import com.lc.ifood.domain.model.MealType.LUNCH
import com.lc.ifood.domain.usecase.GetMealSchedulesUseCase
import com.lc.ifood.domain.usecase.UpdateMealScheduleUseCase
import com.lc.ifood.util.MainDispatcherRule
import io.mockk.MockKAnnotations
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ScheduleAdjustmentViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK private lateinit var getMealSchedules: GetMealSchedulesUseCase
    @MockK private lateinit var updateMealSchedule: UpdateMealScheduleUseCase

    private val scheduleBreakfast = MealSchedule(BREAKFAST, 8, 0)
    private val scheduleLunch = MealSchedule(LUNCH, 12, 0)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun createViewModel(): ScheduleAdjustmentViewModel {
        coJustRun { updateMealSchedule.invoke(any()) }
        return ScheduleAdjustmentViewModel(getMealSchedules, updateMealSchedule)
    }

    @Test
    fun `uiState schedules are populated from use case`() = runTest {
        every { getMealSchedules.invoke() } returns flowOf(listOf(scheduleBreakfast, scheduleLunch))
        val vm = createViewModel()
        val initial = vm.uiState.first()
        assertEquals(listOf(scheduleBreakfast, scheduleLunch), initial.schedules)
    }

    @Test
    fun `updateTime modifies the correct schedule in state`() = runTest {
        every { getMealSchedules.invoke() } returns flowOf(listOf(scheduleBreakfast))
        val vm = createViewModel()
        vm.updateTime(scheduleBreakfast, 9, 30)
        val state = vm.uiState.value
        assertEquals(1, state.schedules.size)
        assertEquals(9, state.schedules[0].hour)
        assertEquals(30, state.schedules[0].minute)
    }

    @Test
    fun `updateTime does not modify other schedules`() = runTest {
        every { getMealSchedules.invoke() } returns flowOf(listOf(scheduleBreakfast, scheduleLunch))
        val vm = createViewModel()
        vm.updateTime(scheduleBreakfast, 9, 30)
        val lunchSchedule = vm.uiState.value.schedules.find { it.mealType == LUNCH }
        assertEquals(12, lunchSchedule?.hour)
        assertEquals(0, lunchSchedule?.minute)
    }

    @Test
    fun `saveAll calls UpdateMealScheduleUseCase for each schedule`() = runTest {
        every { getMealSchedules.invoke() } returns flowOf(listOf(scheduleBreakfast, scheduleLunch))
        val vm = createViewModel()
        var doneCalled = false
        vm.saveAll { doneCalled = true }
        advanceUntilIdle()
        coVerify { updateMealSchedule.invoke(scheduleBreakfast) }
        coVerify { updateMealSchedule.invoke(scheduleLunch) }
        assertTrue(doneCalled)
    }

    @Test
    fun `saveAll sets saved to true after completion`() = runTest {
        every { getMealSchedules.invoke() } returns flowOf(listOf(scheduleBreakfast))
        val vm = createViewModel()
        vm.saveAll {}
        advanceUntilIdle()
        assertTrue(vm.uiState.value.saved)
        assertFalse(vm.uiState.value.isSaving)
    }
}
