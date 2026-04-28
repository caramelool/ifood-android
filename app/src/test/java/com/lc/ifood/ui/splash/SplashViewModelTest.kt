package com.lc.ifood.ui.splash

import com.lc.ifood.domain.permission.NotificationPermissionChecker
import com.lc.ifood.domain.usecase.GetOnboardingStatusUseCase
import com.lc.ifood.util.MainDispatcherRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    @MockK private lateinit var permissionChecker: NotificationPermissionChecker
    @MockK private lateinit var getOnboardingStatus: GetOnboardingStatusUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun createViewModel() = SplashViewModel(permissionChecker, getOnboardingStatus)

    @Test
    fun `destination emits Loading then Home when onboarding is completed and permission granted`() = runTest(testDispatcher) {
        every { permissionChecker.isGranted() } returns true
        every { getOnboardingStatus.invoke() } returns flowOf(true)

        val vm = createViewModel()
        val items = vm.destination.take(2).toList()

        assertEquals(SplashDestination.Loading, items[0])
        assertEquals(SplashDestination.Home, items[1])
    }

    @Test
    fun `destination emits Loading then PermissionDenied when onboarding is completed but permission denied`() = runTest(testDispatcher) {
        every { permissionChecker.isGranted() } returns false
        every { getOnboardingStatus.invoke() } returns flowOf(true)

        val vm = createViewModel()
        val items = vm.destination.take(2).toList()

        assertEquals(SplashDestination.Loading, items[0])
        assertEquals(SplashDestination.PermissionDenied, items[1])
    }

    @Test
    fun `destination emits Loading then Onboarding when onboarding is not completed`() = runTest(testDispatcher) {
        every { permissionChecker.isGranted() } returns false
        every { getOnboardingStatus.invoke() } returns flowOf(false)

        val vm = createViewModel()
        val items = vm.destination.take(2).toList()

        assertEquals(SplashDestination.Loading, items[0])
        assertEquals(SplashDestination.Onboarding, items[1])
    }
}
