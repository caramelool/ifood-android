package com.lc.ifood.data.db.dao

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.lc.ifood.data.db.AppDatabase
import com.lc.ifood.data.db.entity.UserEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    private lateinit var db: AppDatabase
    private lateinit var dao: UserDao

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.userDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun getUserEmitsNullWhenTableIsEmpty() = runBlocking {
        val result = dao.getUser().first()
        assertNull(result)
    }

    @Test
    fun insertAndGetUser() = runBlocking {
        dao.insert(UserEntity(name = "Lucas"))

        val result = dao.getUser().first()

        assertEquals("Lucas", result?.name)
    }

    @Test
    fun getUserReturnsOnlyFirstUser() = runBlocking {
        dao.insert(UserEntity(name = "Lucas"))
        dao.insert(UserEntity(name = "Maria"))

        val result = dao.getUser().first()

        assertEquals("Lucas", result?.name)
    }

    @Test
    fun getUserAutoGeneratesId() = runBlocking {
        dao.insert(UserEntity(name = "Lucas"))

        val result = dao.getUser().first()

        assertEquals(1, result?.id)
    }
}
