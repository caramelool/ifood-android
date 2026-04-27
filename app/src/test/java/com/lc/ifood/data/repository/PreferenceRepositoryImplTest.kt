package com.lc.ifood.data.repository

import com.lc.ifood.data.db.dao.UserPreferenceDao
import com.lc.ifood.data.db.entity.UserPreferenceEntity
import com.lc.ifood.domain.model.MealType.BREAKFAST
import com.lc.ifood.domain.model.MealType.LUNCH
import com.lc.ifood.domain.model.UserPreference
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.runs
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
class PreferenceRepositoryImplTest {

    @MockK private lateinit var dao: UserPreferenceDao

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun createRepository() = PreferenceRepositoryImpl(dao)

    @Test
    fun `getPreferences maps entities from dao to domain models`() = runTest {
        val entity = UserPreferenceEntity(id = 1, label = "Saudável", mealTypes = "BREAKFAST")
        every { dao.getAll() } returns flowOf(listOf(entity))

        val result = createRepository().getPreferences().first()
        assertEquals(1, result.size)
        assertEquals(UserPreference(1, "Saudável", listOf(BREAKFAST)), result[0])
    }

    @Test
    fun `getPreferences parses multiple meal types from CSV`() = runTest {
        val entity = UserPreferenceEntity(id = 1, label = "Flex", mealTypes = "BREAKFAST,LUNCH")
        every { dao.getAll() } returns flowOf(listOf(entity))

        val result = createRepository().getPreferences().first()
        assertEquals(listOf(BREAKFAST, LUNCH), result[0].mealTypes)
    }

    @Test
    fun `getPreferencesByMealType returns filtered preferences from dao`() = runTest {
        val entity = UserPreferenceEntity(id = 2, label = "Vegano", mealTypes = "LUNCH")
        coEvery { dao.getByMealType("LUNCH") } returns listOf(entity)

        val result = createRepository().getPreferencesByMealType(LUNCH)

        assertEquals(1, result.size)
        assertEquals(UserPreference(2, "Vegano", listOf(LUNCH)), result[0])
    }

    @Test
    fun `addPreference converts meals to CSV and inserts into dao`() = runTest {
        val preference = UserPreference(id = 0, label = "Saudável", mealTypes = listOf(BREAKFAST, LUNCH))
        coEvery { dao.insert(any()) } just runs

        createRepository().addPreference(preference)

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
