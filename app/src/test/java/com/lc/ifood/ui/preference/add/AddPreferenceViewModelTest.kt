package com.lc.ifood.ui.preference.add

import app.cash.turbine.test
import com.lc.ifood.domain.model.MealType.BREAKFAST
import com.lc.ifood.domain.model.MealType.LUNCH
import com.lc.ifood.domain.usecase.GetMealsUseCase
import com.lc.ifood.domain.usecase.SavePreferenceUseCase
import com.lc.ifood.util.MainDispatcherRule
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AddPreferenceViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getMeals: GetMealsUseCase = mockk()
    private val savePreference: SavePreferenceUseCase = mockk()

    private fun createViewModel(): AddPreferenceViewModel {
        coJustRun { savePreference.invoke(any(), any()) }
        return AddPreferenceViewModel(getMeals, savePreference)
    }

    @Test
    fun `mealOptions are populated from use case`() = runTest {
        every { getMeals.invoke() } returns flowOf(listOf(BREAKFAST, LUNCH))
        val vm = createViewModel()
        vm.uiState.test {
            val state = awaitItem()
            assertEquals(listOf(BREAKFAST, LUNCH), state.mealTypeOptions)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onLabelChange updates label in state`() = runTest {
        every { getMeals.invoke() } returns flowOf(emptyList())
        val vm = createViewModel()
        vm.onLabelChange("Saudável")
        assertEquals("Saudável", vm.uiState.value.label)
    }

    @Test
    fun `toggleMeal adds meal to selectedMeals`() = runTest {
        every { getMeals.invoke() } returns flowOf(listOf(BREAKFAST))
        val vm = createViewModel()
        vm.toggleMealType(BREAKFAST)
        assertTrue(vm.uiState.value.selectedMealTypes.contains(BREAKFAST))
    }

    @Test
    fun `toggleMeal removes meal when already selected`() = runTest {
        every { getMeals.invoke() } returns flowOf(listOf(BREAKFAST))
        val vm = createViewModel()
        vm.toggleMealType(BREAKFAST)
        vm.toggleMealType(BREAKFAST)
        assertFalse(vm.uiState.value.selectedMealTypes.contains(BREAKFAST))
    }

    @Test
    fun `canSave is false when label is blank`() = runTest {
        every { getMeals.invoke() } returns flowOf(listOf(BREAKFAST))
        val vm = createViewModel()
        vm.toggleMealType(BREAKFAST)
        assertFalse(vm.uiState.value.canSave)
    }

    @Test
    fun `canSave is false when no meal selected`() = runTest {
        every { getMeals.invoke() } returns flowOf(listOf(BREAKFAST))
        val vm = createViewModel()
        vm.onLabelChange("Saudável")
        assertFalse(vm.uiState.value.canSave)
    }

    @Test
    fun `canSave is true when label and meal are set`() = runTest {
        every { getMeals.invoke() } returns flowOf(listOf(BREAKFAST))
        val vm = createViewModel()
        vm.onLabelChange("Saudável")
        vm.toggleMealType(BREAKFAST)
        assertTrue(vm.uiState.value.canSave)
    }

    @Test
    fun `save calls SavePreferenceUseCase with trimmed label and selected meals`() = runTest {
        every { getMeals.invoke() } returns flowOf(listOf(BREAKFAST))
        val vm = createViewModel()
        vm.onLabelChange("  Saudável  ")
        vm.toggleMealType(BREAKFAST)
        vm.save {}
        advanceUntilIdle()
        coVerify { savePreference.invoke("Saudável", listOf(BREAKFAST)) }
    }

    @Test
    fun `save invokes onDone callback on success`() = runTest {
        every { getMeals.invoke() } returns flowOf(listOf(BREAKFAST))
        val vm = createViewModel()
        vm.onLabelChange("Saudável")
        vm.toggleMealType(BREAKFAST)
        var doneCalled = false
        vm.save { doneCalled = true }
        advanceUntilIdle()
        assertTrue(doneCalled)
    }

    @Test
    fun `save does not call SavePreferenceUseCase when canSave is false`() = runTest {
        every { getMeals.invoke() } returns flowOf(listOf(BREAKFAST))
        val vm = createViewModel()
        vm.save {}
        advanceUntilIdle()
        coVerify(exactly = 0) { savePreference.invoke(any(), any()) }
    }

    @Test
    fun `save sets saved true after completion`() = runTest {
        every { getMeals.invoke() } returns flowOf(listOf(BREAKFAST))
        val vm = createViewModel()
        vm.onLabelChange("Saudável")
        vm.toggleMealType(BREAKFAST)
        vm.save {}
        advanceUntilIdle()
        assertTrue(vm.uiState.value.saved)
        assertFalse(vm.uiState.value.isSaving)
    }
}
