package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.repository.UserRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SaveUserUseCaseTest {

    private val repository: UserRepository = mockk()
    private val useCase = SaveUserUseCase(repository)

    @Test
    fun `invoke delegates to repository saveUser with given name`() = runTest {
        coEvery { repository.saveUser("Lucas") } just runs

        useCase("Lucas")

        coVerify { repository.saveUser("Lucas") }
    }
}
