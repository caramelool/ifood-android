package com.lc.ifood.ui.preference.add

import com.lc.ifood.domain.model.MealType.BREAKFAST
import com.lc.ifood.domain.model.MealType.LUNCH
import com.lc.ifood.domain.usecase.GetMealsUseCase
import com.lc.ifood.domain.usecase.SavePreferenceUseCase
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
class AddPreferenceViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK private lateinit var getMeals: GetMealsUseCase
    @MockK private lateinit var savePreference: SavePreferenceUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coJustRun { savePreference.invoke(any(), any()) }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun createViewModel(): AddPreferenceViewModel {
        return AddPreferenceViewModel(getMeals, savePreference)
    }

    @Test
    fun `mealOptions are populated from use case`() = runTest {
        every { getMeals.invoke() } returns flowOf(listOf(BREAKFAST, LUNCH))
        val vm = createViewModel()
        val state = vm.uiState.first()
        assertEquals(listOf(BREAKFAST, LUNCH), state.mealTypeOptions)
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
    fun `save sets showErrors true when form is invalid`() = runTest {
        every { getMeals.invoke() } returns flowOf(listOf(BREAKFAST))
        val vm = createViewModel()
        vm.save {}
        assertTrue(vm.uiState.value.showErrors)
    }

    @Test
    fun `save does not call SavePreferenceUseCase when form is invalid`() = runTest {
        every { getMeals.invoke() } returns flowOf(listOf(BREAKFAST))
        val vm = createViewModel()
        vm.save {}
        advanceUntilIdle()
        coVerify(exactly = 0) { savePreference.invoke(any(), any()) }
    }

    @Test
    fun `labelError is true when showErrors and label is blank`() = runTest {
        every { getMeals.invoke() } returns flowOf(listOf(BREAKFAST))
        val vm = createViewModel()
        vm.save {}
        assertTrue(vm.uiState.value.labelError)
    }

    @Test
    fun `mealTypesError is true when showErrors and no meal selected`() = runTest {
        every { getMeals.invoke() } returns flowOf(listOf(BREAKFAST))
        val vm = createViewModel()
        vm.onLabelChange("Saudável")
        vm.save {}
        assertTrue(vm.uiState.value.mealTypesError)
        assertFalse(vm.uiState.value.labelError)
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
