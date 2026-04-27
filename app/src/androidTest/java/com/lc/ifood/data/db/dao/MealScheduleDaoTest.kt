package com.lc.ifood.data.db.dao

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.lc.ifood.data.db.AppDatabase
import com.lc.ifood.data.db.entity.MealScheduleEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MealScheduleDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: MealScheduleDao

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.mealScheduleDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertAllAndGetAll() = runBlocking {
        val entities = listOf(
            MealScheduleEntity("BREAKFAST", 8, 0),
            MealScheduleEntity("LUNCH", 13, 0)
        )
        dao.insertAll(entities)

        val result = dao.getAll().first()

        assertEquals(2, result.size)
    }

    @Test
    fun countReturnsCorrectNumber() = runBlocking {
        assertEquals(0, dao.count())

        dao.insertAll(listOf(MealScheduleEntity("BREAKFAST", 8, 0)))

        assertEquals(1, dao.count())
    }

    @Test
    fun upsertUpdatesExistingRecord() = runBlocking {
        dao.insertAll(listOf(MealScheduleEntity("BREAKFAST", 8, 0)))

        dao.upsert(MealScheduleEntity("BREAKFAST", 9, 30))

        val result = dao.getAllOnce()
        assertEquals(1, result.size)
        assertEquals(9, result[0].hour)
        assertEquals(30, result[0].minute)
    }

    @Test
    fun upsertInsertsNewRecord() = runBlocking {
        dao.upsert(MealScheduleEntity("BREAKFAST", 8, 0))

        val result = dao.getAllOnce()
        assertEquals(1, result.size)
        assertEquals("BREAKFAST", result[0].mealType)
    }

    @Test
    fun getAllOnceReturnsSameDataAsFlow() = runBlocking {
        dao.insertAll(listOf(
            MealScheduleEntity("BREAKFAST", 8, 0),
            MealScheduleEntity("DINNER", 21, 0)
        ))

        val fromFlow = dao.getAll().first()
        val fromOnce = dao.getAllOnce()

        assertEquals(fromFlow.map { it.mealType }.sorted(), fromOnce.map { it.mealType }.sorted())
    }
}
