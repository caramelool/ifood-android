package com.lc.ifood.ui.preference.add

import app.cash.turbine.test
import com.lc.ifood.domain.model.Meal
import com.lc.ifood.domain.model.MealType
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

    private val breakfast = Meal(MealType.BREAKFAST, "Café da Manhã", "Café")
    private val lunch = Meal(MealType.LUNCH, "Almoço", "Almoço")

    private fun createViewModel(): AddPreferenceViewModel {
        coJustRun { savePreference.invoke(any(), any()) }
        return AddPreferenceViewModel(getMeals, savePreference)
    }

    @Test
    fun `mealOptions are populated from use case`() = runTest {
        every { getMeals.invoke() } returns flowOf(listOf(breakfast, lunch))
        val vm = createViewModel()
        vm.uiState.test {
            val state = awaitItem()
            assertEquals(listOf(breakfast, lunch), state.mealOptions)
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
        every { getMeals.invoke() } returns flowOf(listOf(breakfast))
        val vm = createViewModel()
        vm.toggleMeal(breakfast)
        assertTrue(vm.uiState.value.selectedMeals.contains(breakfast))
    }

    @Test
    fun `toggleMeal removes meal when already selected`() = runTest {
        every { getMeals.invoke() } returns flowOf(listOf(breakfast))
        val vm = createViewModel()
        vm.toggleMeal(breakfast)
        vm.toggleMeal(breakfast)
        assertFalse(vm.uiState.value.selectedMeals.contains(breakfast))
    }

    @Test
    fun `canSave is false when label is blank`() = runTest {
        every { getMeals.invoke() } returns flowOf(listOf(breakfast))
        val vm = createViewModel()
        vm.toggleMeal(breakfast)
        assertFalse(vm.uiState.value.canSave)
    }

    @Test
    fun `canSave is false when no meal selected`() = runTest {
        every { getMeals.invoke() } returns flowOf(listOf(breakfast))
        val vm = createViewModel()
        vm.onLabelChange("Saudável")
        assertFalse(vm.uiState.value.canSave)
    }

    @Test
    fun `canSave is true when label and meal are set`() = runTest {
        every { getMeals.invoke() } returns flowOf(listOf(breakfast))
        val vm = createViewModel()
        vm.onLabelChange("Saudável")
        vm.toggleMeal(breakfast)
        assertTrue(vm.uiState.value.canSave)
    }

    @Test
    fun `save calls SavePreferenceUseCase with trimmed label and selected meals`() = runTest {
        every { getMeals.invoke() } returns flowOf(listOf(breakfast))
        val vm = createViewModel()
        vm.onLabelChange("  Saudável  ")
        vm.toggleMeal(breakfast)
        vm.save {}
        advanceUntilIdle()
        coVerify { savePreference.invoke("Saudável", listOf(breakfast)) }
    }

    @Test
    fun `save invokes onDone callback on success`() = runTest {
        every { getMeals.invoke() } returns flowOf(listOf(breakfast))
        val vm = createViewModel()
        vm.onLabelChange("Saudável")
        vm.toggleMeal(breakfast)
        var doneCalled = false
        vm.save { doneCalled = true }
        advanceUntilIdle()
        assertTrue(doneCalled)
    }

    @Test
    fun `save does not call SavePreferenceUseCase when canSave is false`() = runTest {
        every { getMeals.invoke() } returns flowOf(listOf(breakfast))
        val vm = createViewModel()
        vm.save {}
        advanceUntilIdle()
        coVerify(exactly = 0) { savePreference.invoke(any(), any()) }
    }

    @Test
    fun `save sets saved true after completion`() = runTest {
        every { getMeals.invoke() } returns flowOf(listOf(breakfast))
        val vm = createViewModel()
        vm.onLabelChange("Saudável")
        vm.toggleMeal(breakfast)
        vm.save {}
        advanceUntilIdle()
        assertTrue(vm.uiState.value.saved)
        assertFalse(vm.uiState.value.isSaving)
    }
}
