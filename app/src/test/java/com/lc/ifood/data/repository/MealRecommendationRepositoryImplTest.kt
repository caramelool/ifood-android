package com.lc.ifood.data.repository

import com.lc.ifood.data.remote.MealRecommendationResponse
import com.lc.ifood.data.remote.MealReminderApiService
import com.lc.ifood.domain.model.MealRecommendation
import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.model.MealType.BREAKFAST
import com.lc.ifood.domain.model.User
import com.lc.ifood.domain.repository.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MealRecommendationRepositoryImplTest {

    @MockK private lateinit var apiService: MealReminderApiService
    @MockK private lateinit var userRepository: UserRepository

    private val schedule = MealSchedule(BREAKFAST, 8, 0)
    private val user = User(1, "Lucas")
    private val apiResponse = MealRecommendationResponse(
        userName = "Lucas",
        mealType = "breakfast",
        placeName = "Café Central",
        placeAddress = "Rua das Flores, 10",
        mealName = "Omelete",
        mealDescription = "Omelete com queijo",
        mealPrice = 18.90,
        preferences = listOf("Saudável"),
        mealImageUrl = "https://cafe-central.com/omelete.jpg"
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun createRepository() = MealRecommendationRepositoryImpl(apiService, userRepository)

    @Test
    fun `getRecommendation fetches user and calls api with correct params`() = runTest {
        every { userRepository.getUser() } returns flowOf(user)
        coEvery {
            apiService.getRecommendation("Lucas", "breakfast", listOf("Saudável"))
        } returns apiResponse

        createRepository().getRecommendation(schedule, listOf("Saudável"))

        coVerify { apiService.getRecommendation("Lucas", "breakfast", listOf("Saudável")) }
    }

    @Test
    fun `getRecommendation maps api response to domain model`() = runTest {
        every { userRepository.getUser() } returns flowOf(user)
        coEvery {
            apiService.getRecommendation(any(), any(), any())
        } returns apiResponse

        val result = createRepository().getRecommendation(schedule, listOf("Saudável"))

        assertEquals(
            MealRecommendation(
                mealType = BREAKFAST,
                placeName = "Café Central",
                placeAddress = "Rua das Flores, 10",
                mealName = "Omelete",
                mealDescription = "Omelete com queijo",
                mealPrice = 18.90,
                preferences = listOf("Saudável"),
                mealImageUrl = "https://cafe-central.com/omelete.jpg"
            ),
            result
        )
    }

    @Test
    fun `getRecommendation passes meal type in lowercase to api`() = runTest {
        every { userRepository.getUser() } returns flowOf(user)
        coEvery {
            apiService.getRecommendation(any(), any(), any())
        } returns apiResponse

        createRepository().getRecommendation(schedule, emptyList())

        coVerify { apiService.getRecommendation(any(), "breakfast", any()) }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `getRecommendation throws when user is null`() = runTest {
        every { userRepository.getUser() } returns flowOf(null)

        createRepository().getRecommendation(schedule, emptyList())
    }
}
