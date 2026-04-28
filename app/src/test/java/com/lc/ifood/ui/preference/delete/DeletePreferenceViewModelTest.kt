package com.lc.ifood.ui.preference.delete

import com.lc.ifood.domain.usecase.DeletePreferenceUseCase
import com.lc.ifood.util.MainDispatcherRule
import io.mockk.MockKAnnotations
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeletePreferenceViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @MockK private lateinit var deletePreference: DeletePreferenceUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        coJustRun { deletePreference.invoke(any()) }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun createViewModel(): DeletePreferenceViewModel {
        return DeletePreferenceViewModel(deletePreference)
    }

    @Test
    fun `delete calls DeletePreferenceUseCase with given id`() = runTest {
        val vm = createViewModel()
        vm.delete(42)
        advanceUntilIdle()
        coVerify { deletePreference.invoke(42) }
    }

    @Test
    fun `delete with different id calls use case with correct id`() = runTest {
        val vm = createViewModel()
        vm.delete(7)
        advanceUntilIdle()
        coVerify { deletePreference.invoke(7) }
    }
}
