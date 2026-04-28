package com.lc.ifood.data.repository

import com.lc.ifood.data.db.dao.UserDao
import com.lc.ifood.data.db.entity.UserEntity
import com.lc.ifood.domain.model.User
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryImplTest {

    @MockK private lateinit var dao: UserDao

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun createRepository() = UserRepositoryImpl(dao)

    @Test
    fun `getUser maps entity from dao to domain model`() = runTest {
        every { dao.getUser() } returns flowOf(UserEntity(id = 1, name = "Lucas"))

        assertEquals(User(1, "Lucas"), createRepository().getUser().first())
    }

    @Test
    fun `getUser emits null when dao emits null entity`() = runTest {
        every { dao.getUser() } returns flowOf(null)

        assertEquals(null, createRepository().getUser().first())
    }

    @Test
    fun `getUser uses cache on second call and does not query dao again`() = runTest {
        every { dao.getUser() } returns flowOf(UserEntity(id = 1, name = "Lucas"))
        val repo = createRepository()

        repo.getUser().first()
        assertEquals(User(1, "Lucas"), repo.getUser().first())

        verify(exactly = 1) { dao.getUser() }
    }

    @Test
    fun `saveUser inserts entity into dao`() = runTest {
        coEvery { dao.insert(any()) } just runs

        createRepository().saveUser("Lucas")

        coVerify { dao.insert(UserEntity(name = "Lucas")) }
    }

    @Test
    fun `saveUser updates cache so next getUser does not hit dao`() = runTest {
        coEvery { dao.insert(any()) } just runs
        val repo = createRepository()

        repo.saveUser("Lucas")

        assertEquals(User(0, "Lucas"), repo.getUser().first())
        verify(exactly = 0) { dao.getUser() }
    }
}
