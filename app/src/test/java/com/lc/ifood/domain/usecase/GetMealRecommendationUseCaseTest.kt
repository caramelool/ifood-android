package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.MealRecommendation
import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.model.MealType.BREAKFAST
import com.lc.ifood.domain.model.UserPreference
import com.lc.ifood.domain.repository.MealRecommendationRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetMealRecommendationUseCaseTest {

    private val repository: MealRecommendationRepository = mockk()
    private val useCase = GetMealRecommendationUseCase(repository)

    private val schedule = MealSchedule(BREAKFAST, 8, 0)
    private val preferences = listOf(
        UserPreference(1, "Saudável"),
        UserPreference(2, "Vegano")
    )
    private val recommendation = MealRecommendation(
        mealType = BREAKFAST,
        placeName = "Restaurante",
        placeAddress = "Rua A",
        mealName = "Omelete",
        mealDescription = "Delicioso",
        mealPrice = 15.0,
        preferences = listOf("Saudável", "Vegano")
    )

    @Test
    fun `invoke maps preference labels before calling repository`() = runTest {
        coEvery {
            repository.getRecommendation(schedule, listOf("Saudável", "Vegano"))
        } returns recommendation

        useCase(schedule, preferences)

        coVerify { repository.getRecommendation(schedule, listOf("Saudável", "Vegano")) }
    }

    @Test
    fun `invoke returns recommendation from repository`() = runTest {
        coEvery {
            repository.getRecommendation(schedule, listOf("Saudável", "Vegano"))
        } returns recommendation

        val result = useCase(schedule, preferences)

        assertEquals(recommendation, result)
    }

    @Test
    fun `invoke passes empty labels when preferences list is empty`() = runTest {
        coEvery { repository.getRecommendation(schedule, emptyList()) } returns recommendation

        useCase(schedule, emptyList())

        coVerify { repository.getRecommendation(schedule, emptyList()) }
    }
}
