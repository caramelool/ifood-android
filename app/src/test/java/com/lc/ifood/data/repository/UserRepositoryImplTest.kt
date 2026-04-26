package com.lc.ifood.data.repository

import app.cash.turbine.test
import com.lc.ifood.data.db.dao.UserDao
import com.lc.ifood.data.db.entity.UserEntity
import com.lc.ifood.domain.model.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryImplTest {

    private val dao: UserDao = mockk()

    private fun createRepository() = UserRepositoryImpl(dao)

    @Test
    fun `getUser maps entity from dao to domain model`() = runTest {
        every { dao.getUser() } returns flowOf(UserEntity(id = 1, name = "Lucas"))

        createRepository().getUser().test {
            assertEquals(User(1, "Lucas"), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getUser emits null when dao emits null entity`() = runTest {
        every { dao.getUser() } returns flowOf(null)

        createRepository().getUser().test {
            assertEquals(null, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getUser uses cache on second call and does not query dao again`() = runTest {
        every { dao.getUser() } returns flowOf(UserEntity(id = 1, name = "Lucas"))
        val repo = createRepository()

        repo.getUser().test {
            awaitItem()
            cancelAndIgnoreRemainingEvents()
        }

        repo.getUser().test {
            assertEquals(User(1, "Lucas"), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

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

        repo.getUser().test {
            assertEquals(User(0, "Lucas"), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        verify(exactly = 0) { dao.getUser() }
    }
}
