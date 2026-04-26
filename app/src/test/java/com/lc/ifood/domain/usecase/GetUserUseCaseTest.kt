package com.lc.ifood.domain.usecase

import app.cash.turbine.test
import com.lc.ifood.domain.model.User
import com.lc.ifood.domain.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetUserUseCaseTest {

    private val repository: UserRepository = mockk()
    private val useCase = GetUserUseCase(repository)

    @Test
    fun `invoke returns flow from repository`() = runTest {
        val user = User(1, "Lucas")
        every { repository.getUser() } returns flowOf(user)

        useCase().test {
            assertEquals(user, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `invoke returns null when repository emits null`() = runTest {
        every { repository.getUser() } returns flowOf(null)

        useCase().test {
            assertEquals(null, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}
