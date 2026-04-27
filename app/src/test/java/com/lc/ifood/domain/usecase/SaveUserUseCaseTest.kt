package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.repository.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.runs
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SaveUserUseCaseTest {

    @MockK private lateinit var repository: UserRepository
    private lateinit var useCase: SaveUserUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = SaveUserUseCase(repository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `invoke delegates to repository saveUser with given name`() = runTest {
        coEvery { repository.saveUser("Lucas") } just runs

        useCase("Lucas")

        coVerify { repository.saveUser("Lucas") }
    }
}
