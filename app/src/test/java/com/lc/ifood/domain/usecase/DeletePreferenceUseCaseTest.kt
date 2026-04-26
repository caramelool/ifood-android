package com.lc.ifood.domain.usecase

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
class DeletePreferenceUseCaseTest {

    private val repository: PreferenceRepository = mockk()
    private val useCase = DeletePreferenceUseCase(repository)

    @Test
    fun `invoke delegates to repository deletePreference with given id`() = runTest {
        coEvery { repository.deletePreference(42) } just runs

        useCase(42)

        coVerify { repository.deletePreference(42) }
    }
}
