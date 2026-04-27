package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.UserPreference
import com.lc.ifood.domain.repository.PreferenceRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.every
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetPreferencesUseCaseTest {

    @MockK private lateinit var repository: PreferenceRepository
    private lateinit var useCase: GetPreferencesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetPreferencesUseCase(repository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `invoke returns flow of preferences from repository`() = runTest {
        val preferences = listOf(UserPreference(1, "Saudável"))
        every { repository.getPreferences() } returns flowOf(preferences)

        assertEquals(preferences, useCase().first())
    }

    @Test
    fun `invoke returns empty list when repository emits empty`() = runTest {
        every { repository.getPreferences() } returns flowOf(emptyList())

        assertEquals(emptyList<UserPreference>(), useCase().first())
    }
}
