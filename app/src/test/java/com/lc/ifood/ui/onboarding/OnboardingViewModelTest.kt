package com.lc.ifood.ui.onboarding

import com.lc.ifood.domain.permission.NotificationPermissionChecker
import com.lc.ifood.domain.usecase.CompleteOnboardingUseCase
import com.lc.ifood.util.MainDispatcherRule
import io.mockk.MockKAnnotations
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
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
class OnboardingViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    @MockK private lateinit var completeOnboarding: CompleteOnboardingUseCase
    @MockK private lateinit var permissionChecker: NotificationPermissionChecker

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coJustRun { completeOnboarding.invoke() }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun createViewModel() = OnboardingViewModel(completeOnboarding, permissionChecker)

    @Test
    fun `pages initializes with 4 onboarding pages`() = runTest(testDispatcher) {
        val vm = createViewModel()
        assertEquals(4, vm.uiState.value.pages.size)
    }

    @Test
    fun `isFabVisible starts as false`() = runTest(testDispatcher) {
        val vm = createViewModel()
        assertFalse(vm.uiState.value.isFabVisible)
    }

    @Test
    fun `onPageChanged on last page sets isFabVisible and isLastPage true`() = runTest(testDispatcher) {
        val vm = createViewModel()
        val lastIndex = vm.uiState.value.pages.size - 1
        vm.onPageChanged(lastIndex)
        assertTrue(vm.uiState.value.isFabVisible)
        assertTrue(vm.uiState.value.isLastPage)
    }

    @Test
    fun `onPageChanged on intermediate page sets isFabVisible false then true after delay`() = runTest(testDispatcher) {
        val vm = createViewModel()
        vm.onPageChanged(1)
        assertFalse(vm.uiState.value.isFabVisible)
        advanceTimeBy(2_000)
        assertTrue(vm.uiState.value.isFabVisible)
    }

    @Test
    fun `onFabClicked calls CompleteOnboardingUseCase`() = runTest(testDispatcher) {
        every { permissionChecker.isGranted() } returns true
        val vm = createViewModel()
        val deferred = async { vm.events.first() }
        vm.onFabClicked()
        advanceUntilIdle()
        deferred.await()
        coVerify { completeOnboarding.invoke() }
    }

    @Test
    fun `onFabClicked emits NavigateToHome when permission is granted`() = runTest(testDispatcher) {
        every { permissionChecker.isGranted() } returns true
        val vm = createViewModel()
        val deferred = async { vm.events.first() }
        vm.onFabClicked()
        advanceUntilIdle()
        assertEquals(OnboardingEvent.NavigateToHome, deferred.await())
    }

    @Test
    fun `onFabClicked emits RequestNotificationPermission when permission is not granted`() = runTest(testDispatcher) {
        every { permissionChecker.isGranted() } returns false
        val vm = createViewModel()
        val deferred = async { vm.events.first() }
        vm.onFabClicked()
        advanceUntilIdle()
        assertEquals(OnboardingEvent.RequestNotificationPermission, deferred.await())
    }

    @Test
    fun `onPermissionResult true emits NavigateToHome`() = runTest(testDispatcher) {
        val vm = createViewModel()
        val deferred = async { vm.events.first() }
        vm.onPermissionResult(true)
        advanceUntilIdle()
        assertEquals(OnboardingEvent.NavigateToHome, deferred.await())
    }

    @Test
    fun `onPermissionResult false emits ShowPermissionDeniedDialog`() = runTest(testDispatcher) {
        val vm = createViewModel()
        val deferred = async { vm.events.first() }
        vm.onPermissionResult(false)
        advanceUntilIdle()
        assertEquals(OnboardingEvent.ShowPermissionDeniedDialog, deferred.await())
    }
}
