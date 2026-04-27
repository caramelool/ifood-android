package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.MealType
import com.lc.ifood.domain.model.UserPreference
import com.lc.ifood.domain.repository.PreferenceRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.coEvery
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetPreferencesByMealTypeUseCaseTest {

    @MockK private lateinit var repository: PreferenceRepository
    private lateinit var useCase: GetPreferencesByMealTypeUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetPreferencesByMealTypeUseCase(repository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `invoke returns preferences filtered by meal type`() = runTest {
        val expected = listOf(UserPreference(1, "Saudável"))
        coEvery { repository.getPreferencesByMealType(MealType.BREAKFAST) } returns expected

        val result = useCase(MealType.BREAKFAST)

        assertEquals(expected, result)
    }

    @Test
    fun `invoke returns empty list when no preferences match meal type`() = runTest {
        coEvery { repository.getPreferencesByMealType(MealType.DINNER) } returns emptyList()

        val result = useCase(MealType.DINNER)

        assertEquals(emptyList<UserPreference>(), result)
    }
}
