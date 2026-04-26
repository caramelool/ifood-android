package com.lc.ifood.domain.usecase

import com.lc.ifood.domain.mapper.MealMapper
import com.lc.ifood.domain.model.Meal
import com.lc.ifood.domain.model.MealType
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class CreateMealScheduleUseCaseTest {

    private val mealMapper: MealMapper = mockk()
    private val useCase = CreateMealScheduleUseCase(mealMapper)

    private val breakfast = Meal(MealType.BREAKFAST, "Café da Manhã", "Café")

    @Test
    fun `invoke builds MealSchedule using mapper for given type and time`() {
        every { mealMapper.map(MealType.BREAKFAST) } returns breakfast

        val result = useCase(MealType.BREAKFAST, 8, 30)

        assertEquals(breakfast, result.meal)
        assertEquals(8, result.hour)
        assertEquals(30, result.minute)
    }

    @Test
    fun `invoke time property formats hour and minute correctly`() {
        every { mealMapper.map(MealType.BREAKFAST) } returns breakfast

        val result = useCase(MealType.BREAKFAST, 8, 5)

        assertEquals("08:05", result.time)
    }
}
