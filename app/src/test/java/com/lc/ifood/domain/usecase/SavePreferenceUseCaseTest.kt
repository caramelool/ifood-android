package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.Meal
import com.lc.ifood.domain.model.MealType
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

    private val meals = listOf(Meal(MealType.BREAKFAST, "Café da Manhã", "Café"))

    @Test
    fun `invoke delegates to repository addPreference with label and meals`() = runTest {
        coEvery { repository.addPreference("Saudável", meals) } just runs

        useCase("Saudável", meals)

        coVerify { repository.addPreference("Saudável", meals) }
    }
}
