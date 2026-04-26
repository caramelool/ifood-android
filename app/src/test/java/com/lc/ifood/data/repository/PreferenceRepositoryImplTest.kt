package com.lc.ifood.data.repository

import app.cash.turbine.test
import com.lc.ifood.data.db.dao.UserPreferenceDao
import com.lc.ifood.data.db.entity.UserPreferenceEntity
import com.lc.ifood.domain.mapper.MealMapper
import com.lc.ifood.domain.model.Meal
import com.lc.ifood.domain.model.MealType
import com.lc.ifood.domain.model.UserPreference
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PreferenceRepositoryImplTest {

    private val dao: UserPreferenceDao = mockk()
    private val mealMapper: MealMapper = mockk()

    private val breakfast = Meal(MealType.BREAKFAST, "Café da Manhã", "Café")
    private val lunch = Meal(MealType.LUNCH, "Almoço", "Almoço")

    private fun createRepository() = PreferenceRepositoryImpl(dao, mealMapper)

    @Test
    fun `getPreferences maps entities from dao to domain models`() = runTest {
        val entity = UserPreferenceEntity(id = 1, label = "Saudável", mealTypes = "BREAKFAST")
        every { dao.getAll() } returns flowOf(listOf(entity))
        every { mealMapper.map(MealType.BREAKFAST) } returns breakfast

        createRepository().getPreferences().test {
            val result = awaitItem()
            assertEquals(1, result.size)
            assertEquals(UserPreference(1, "Saudável", listOf(breakfast)), result[0])
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getPreferences parses multiple meal types from CSV`() = runTest {
        val entity = UserPreferenceEntity(id = 1, label = "Flex", mealTypes = "BREAKFAST,LUNCH")
        every { dao.getAll() } returns flowOf(listOf(entity))
        every { mealMapper.map(MealType.BREAKFAST) } returns breakfast
        every { mealMapper.map(MealType.LUNCH) } returns lunch

        createRepository().getPreferences().test {
            val result = awaitItem()
            assertEquals(listOf(breakfast, lunch), result[0].meals)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getPreferencesByMealType returns filtered preferences from dao`() = runTest {
        val entity = UserPreferenceEntity(id = 2, label = "Vegano", mealTypes = "LUNCH")
        coEvery { dao.getByMealType("LUNCH") } returns listOf(entity)
        every { mealMapper.map(MealType.LUNCH) } returns lunch

        val result = createRepository().getPreferencesByMealType(MealType.LUNCH)

        assertEquals(1, result.size)
        assertEquals(UserPreference(2, "Vegano", listOf(lunch)), result[0])
    }

    @Test
    fun `addPreference converts meals to CSV and inserts into dao`() = runTest {
        coEvery { dao.insert(any()) } just runs

        createRepository().addPreference("Saudável", listOf(breakfast, lunch))

        coVerify {
            dao.insert(
                UserPreferenceEntity(id = 0, label = "Saudável", mealTypes = "BREAKFAST,LUNCH")
            )
        }
    }

    @Test
    fun `deletePreference calls dao deleteById with given id`() = runTest {
        coEvery { dao.deleteById(5) } just runs

        createRepository().deletePreference(5)

        coVerify { dao.deleteById(5) }
    }
}
