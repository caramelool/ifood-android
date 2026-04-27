package com.lc.ifood.ui.home

import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.model.MealType.BREAKFAST
import com.lc.ifood.domain.model.User
import com.lc.ifood.domain.model.UserPreference
import com.lc.ifood.domain.usecase.GetMealSchedulesUseCase
import com.lc.ifood.domain.usecase.GetPreferencesUseCase
import com.lc.ifood.domain.usecase.GetUserUseCase
import com.lc.ifood.domain.usecase.SaveUserUseCase
import com.lc.ifood.domain.usecase.SeedDefaultSchedulesUseCase
import com.lc.ifood.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getMealSchedules: GetMealSchedulesUseCase = mockk()
    private val getPreferences: GetPreferencesUseCase = mockk()
    private val getUser: GetUserUseCase = mockk()
    private val saveUser: SaveUserUseCase = mockk()
    private val seedDefaultSchedules: SeedDefaultSchedulesUseCase = mockk()

    private val schedule = MealSchedule(BREAKFAST, 8, 0)
    private val preference = UserPreference(1, "Saudável", listOf(BREAKFAST))
    private val user = User(1, "Lucas")

    private fun createViewModel(): HomeViewModel {
        coEvery { seedDefaultSchedules.invoke() } just runs
        return HomeViewModel(getMealSchedules, getPreferences, getUser, saveUser, seedDefaultSchedules)
    }

    @Test
    fun `uiState emits meal schedules from use case`() = runTest {
        every { getMealSchedules.invoke() } returns flowOf(listOf(schedule))
        every { getPreferences.invoke() } returns flowOf(emptyList())
        every { getUser.invoke() } returns flowOf(null)

        val vm = createViewModel()
        val state = vm.uiState.first()
        assertEquals(listOf(schedule), state.mealSchedules)
    }

    @Test
    fun `uiState emits preferences from use case`() = runTest {
        every { getMealSchedules.invoke() } returns flowOf(emptyList())
        every { getPreferences.invoke() } returns flowOf(listOf(preference))
        every { getUser.invoke() } returns flowOf(null)

        val vm = createViewModel()
        val state = vm.uiState.first()
        assertEquals(listOf(preference), state.preferences)
    }

    @Test
    fun `uiState sets userName and isUserLoaded when user exists`() = runTest {
        every { getMealSchedules.invoke() } returns flowOf(emptyList())
        every { getPreferences.invoke() } returns flowOf(emptyList())
        every { getUser.invoke() } returns flowOf(user)

        val vm = createViewModel()
        val state = vm.uiState.first()
        assertEquals("Lucas", state.userName)
        assertTrue(state.isUserLoaded)
    }

    @Test
    fun `uiState sets isUserLoaded true and userName null when no user`() = runTest {
        every { getMealSchedules.invoke() } returns flowOf(emptyList())
        every { getPreferences.invoke() } returns flowOf(emptyList())
        every { getUser.invoke() } returns flowOf(null)

        val vm = createViewModel()
        val state = vm.uiState.first()
        assertEquals(null, state.userName)
        assertTrue(state.isUserLoaded)
    }

    @Test
    fun `saveUserName calls SaveUserUseCase with given name`() = runTest {
        every { getMealSchedules.invoke() } returns flowOf(emptyList())
        every { getPreferences.invoke() } returns flowOf(emptyList())
        every { getUser.invoke() } returns flowOf(null)
        coEvery { saveUser.invoke("Lucas") } just runs

        val vm = createViewModel()
        vm.saveUserName("Lucas")

        coVerify { saveUser.invoke("Lucas") }
    }

    @Test
    fun `init seeds default schedules`() = runTest {
        every { getMealSchedules.invoke() } returns flowOf(emptyList())
        every { getPreferences.invoke() } returns flowOf(emptyList())
        every { getUser.invoke() } returns flowOf(null)

        createViewModel()

        coVerify { seedDefaultSchedules.invoke() }
    }
}
