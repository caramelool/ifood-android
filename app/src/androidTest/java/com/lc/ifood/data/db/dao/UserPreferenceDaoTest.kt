package com.lc.ifood.data.db.dao

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.lc.ifood.data.db.AppDatabase
import com.lc.ifood.data.db.entity.UserPreferenceEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserPreferenceDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: UserPreferenceDao

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.userPreferenceDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertAndGetAll() = runBlocking {
        dao.insert(UserPreferenceEntity(label = "Saudável", mealTypes = "BREAKFAST"))

        val result = dao.getAll().first()

        assertEquals(1, result.size)
        assertEquals("Saudável", result[0].label)
    }

    @Test
    fun deleteByIdRemovesRecord() = runBlocking {
        dao.insert(UserPreferenceEntity(label = "Saudável", mealTypes = "BREAKFAST"))
        val inserted = dao.getAll().first()
        val id = inserted[0].id

        dao.deleteById(id)

        val result = dao.getAll().first()
        assertTrue(result.isEmpty())
    }

    @Test
    fun getByMealTypeMatchesExactMealType() = runBlocking {
        dao.insert(UserPreferenceEntity(label = "Saudável", mealTypes = "BREAKFAST"))

        val result = dao.getByMealType("BREAKFAST")

        assertEquals(1, result.size)
        assertEquals("Saudável", result[0].label)
    }

    @Test
    fun getByMealTypeMatchesMealTypeAtStart() = runBlocking {
        dao.insert(UserPreferenceEntity(label = "Flex", mealTypes = "BREAKFAST,LUNCH"))

        val result = dao.getByMealType("BREAKFAST")

        assertEquals(1, result.size)
    }

    @Test
    fun getByMealTypeMatchesMealTypeAtEnd() = runBlocking {
        dao.insert(UserPreferenceEntity(label = "Flex", mealTypes = "BREAKFAST,LUNCH"))

        val result = dao.getByMealType("LUNCH")

        assertEquals(1, result.size)
    }

    @Test
    fun getByMealTypeMatchesMealTypeInMiddle() = runBlocking {
        dao.insert(UserPreferenceEntity(label = "All", mealTypes = "BREAKFAST,LUNCH,DINNER"))

        val result = dao.getByMealType("LUNCH")

        assertEquals(1, result.size)
    }

    @Test
    fun getByMealTypeDoesNotMatchPartialName() = runBlocking {
        dao.insert(UserPreferenceEntity(label = "Snack", mealTypes = "AFTERNOON_SNACK"))

        val result = dao.getByMealType("SNACK")

        assertTrue(result.isEmpty())
    }

    @Test
    fun getByMealTypeReturnsEmptyWhenNoMatch() = runBlocking {
        dao.insert(UserPreferenceEntity(label = "Saudável", mealTypes = "BREAKFAST"))

        val result = dao.getByMealType("DINNER")

        assertTrue(result.isEmpty())
    }
}
