package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.MealType.BREAKFAST
import com.lc.ifood.domain.model.UserPreference
import com.lc.ifood.domain.repository.PreferenceRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SavePreferenceUseCaseTest {

    private val repository: PreferenceRepository = mockk()
    private val useCase = SavePreferenceUseCase(repository)


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
