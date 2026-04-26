package com.lc.ifood.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesOf
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OnboardingRepositoryImplTest {

    private val dataStore: DataStore<Preferences> = mockk()
    private val onboardingKey = booleanPreferencesKey("onboarding_completed")

    private fun createRepository(): OnboardingRepositoryImpl {
        every { dataStore.data } returns flowOf(emptyPreferences())
        return OnboardingRepositoryImpl(dataStore)
    }

    @Test
    fun `isOnboardingCompleted emits false when key is not set`() = runTest {
        val repo = createRepository()

        repo.isOnboardingCompleted.test {
            assertFalse(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `isOnboardingCompleted emits true when key is set to true`() = runTest {
        every { dataStore.data } returns flowOf(preferencesOf(onboardingKey to true))
        val repo = OnboardingRepositoryImpl(dataStore)

        repo.isOnboardingCompleted.test {
            assertTrue(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `setOnboardingCompleted calls dataStore updateData`() = runTest {
        val repo = createRepository()
        coEvery { dataStore.updateData(any()) } returns mockk()

        repo.setOnboardingCompleted()

        coVerify { dataStore.updateData(any()) }
    }
}
