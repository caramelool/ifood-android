package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.MealType.BREAKFAST
import com.lc.ifood.domain.model.UserPreference
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
class SavePreferenceUseCaseTest {

    @MockK private lateinit var repository: PreferenceRepository
    private lateinit var useCase: SavePreferenceUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = SavePreferenceUseCase(repository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `invoke delegates to repository addPreference with label and meals`() = runTest {
        coEvery { repository.addPreference(any()) } just runs

        useCase("Saudável", listOf(BREAKFAST))

        coVerify {
            repository.addPreference(
                UserPreference(id = 0, label = "Saudável", listOf(BREAKFAST))
            )
        }
    }
}
