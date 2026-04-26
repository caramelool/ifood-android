package com.lc.ifood.domain.usecase

import app.cash.turbine.test
import com.lc.ifood.domain.model.UserPreference
import com.lc.ifood.domain.repository.PreferenceRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetPreferencesUseCaseTest {

    private val repository: PreferenceRepository = mockk()
    private val useCase = GetPreferencesUseCase(repository)

    @Test
    fun `invoke returns flow of preferences from repository`() = runTest {
        val preferences = listOf(UserPreference(1, "Saudável"))
        every { repository.getPreferences() } returns flowOf(preferences)

        useCase().test {
            assertEquals(preferences, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `invoke returns empty list when repository emits empty`() = runTest {
        every { repository.getPreferences() } returns flowOf(emptyList())

        useCase().test {
            assertEquals(emptyList<UserPreference>(), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
