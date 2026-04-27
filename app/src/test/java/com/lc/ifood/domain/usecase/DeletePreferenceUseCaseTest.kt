package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.repository.PreferenceRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.runs
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeletePreferenceUseCaseTest {

    @MockK private lateinit var repository: PreferenceRepository
    private lateinit var useCase: DeletePreferenceUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = DeletePreferenceUseCase(repository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `invoke delegates to repository deletePreference with given id`() = runTest {
        coEvery { repository.deletePreference(42) } just runs

        useCase(42)

        coVerify { repository.deletePreference(42) }
    }
}
