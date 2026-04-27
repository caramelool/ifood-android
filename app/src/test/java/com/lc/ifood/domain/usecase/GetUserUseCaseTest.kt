package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.model.User
import com.lc.ifood.domain.repository.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.every
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
class GetUserUseCaseTest {

    @MockK private lateinit var repository: UserRepository
    private lateinit var useCase: GetUserUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetUserUseCase(repository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `invoke returns flow from repository`() = runTest {
        val user = User(1, "Lucas")
        every { repository.getUser() } returns flowOf(user)

        assertEquals(user, useCase().first())
    }

    @Test
    fun `invoke returns null when repository emits null`() = runTest {
        every { repository.getUser() } returns flowOf(null)

        assertEquals(null, useCase().first())
    }
}
