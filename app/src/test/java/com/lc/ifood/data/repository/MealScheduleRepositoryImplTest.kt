package com.lc.ifood.data.repository

import com.lc.ifood.data.db.dao.MealScheduleDao
import com.lc.ifood.data.db.entity.MealScheduleEntity
import com.lc.ifood.domain.model.MealSchedule
import com.lc.ifood.domain.model.MealType.AFTERNOON_SNACK
import com.lc.ifood.domain.model.MealType.BREAKFAST
import com.lc.ifood.domain.model.MealType.DINNER
import com.lc.ifood.domain.model.MealType.LUNCH
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
class MealScheduleRepositoryImplTest {

    @MockK private lateinit var dao: MealScheduleDao

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun createRepository() = MealScheduleRepositoryImpl(dao)

    @Test
    fun `getMealSchedules maps entities from dao to domain models`() = runTest {
        val entity = MealScheduleEntity(mealType = "BREAKFAST", hour = 8, minute = 0)
        every { dao.getAll() } returns flowOf(listOf(entity))

        assertEquals(listOf(MealSchedule(BREAKFAST, 8, 0)), createRepository().getMealSchedules().first())
    }

    @Test
    fun `getMealSchedules returns default schedules when dao emits empty list`() = runTest {
        every { dao.getAll() } returns flowOf(emptyList())

        val result = createRepository().getMealSchedules().first()
        assertEquals(4, result.size)
        assertEquals(BREAKFAST, result[0].mealType)
        assertEquals(8, result[0].hour)
        assertEquals(LUNCH, result[1].mealType)
        assertEquals(13, result[1].hour)
        assertEquals(AFTERNOON_SNACK, result[2].mealType)
        assertEquals(17, result[2].hour)
        assertEquals(DINNER, result[3].mealType)
        assertEquals(21, result[3].hour)
    }

    @Test
    fun `updateMealSchedule upserts entity into dao`() = runTest {
        coEvery { dao.upsert(any()) } just runs

        createRepository().updateMealSchedule(MealSchedule(BREAKFAST, 9, 30))

        coVerify {
            dao.upsert(MealScheduleEntity(mealType = "BREAKFAST", hour = 9, minute = 30))
        }
    }

    @Test
    fun `seedDefaultsIfEmpty inserts defaults when dao count is zero`() = runTest {
        coEvery { dao.count() } returns 0
        coEvery { dao.insertAll(any()) } just runs

        createRepository().seedDefaultsIfEmpty()

        coVerify { dao.insertAll(any()) }
    }

    @Test
    fun `seedDefaultsIfEmpty does not insert when dao already has records`() = runTest {
        coEvery { dao.count() } returns 4

        createRepository().seedDefaultsIfEmpty()

        coVerify(exactly = 0) { dao.insertAll(any()) }
    }
}
