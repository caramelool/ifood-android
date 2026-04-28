package com.lc.ifood.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.preferencesOf
import com.lc.ifood.domain.repository.OnboardingRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OnboardingRepositoryImplTest {

    @MockK private lateinit var dataStore: DataStore<Preferences>
    private val onboardingKey = booleanPreferencesKey("onboarding_completed")

    private lateinit var repository: OnboardingRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { dataStore.data } returns flowOf(emptyPreferences())
        repository = OnboardingRepositoryImpl(dataStore)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `isOnboardingCompleted emits false when key is not set`() = runTest {
        assertFalse(repository.isOnboardingCompleted.first())
    }

    @Test
    fun `isOnboardingCompleted emits true when key is set to true`() = runTest {
        every { dataStore.data } returns flowOf(preferencesOf(onboardingKey to true))
        assertTrue(repository.isOnboardingCompleted.first())
    }

    @Test
    fun `setOnboardingCompleted calls dataStore updateData`() = runTest {
        coEvery { dataStore.updateData(any()) } returns mockk()

        repository.setOnboardingCompleted()

        coVerify { dataStore.updateData(any()) }
    }
}
