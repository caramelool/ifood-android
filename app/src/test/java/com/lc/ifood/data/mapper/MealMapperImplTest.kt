package com.lc.ifood.data.mapper

import android.content.Context
import com.lc.ifood.R
import com.lc.ifood.domain.model.MealType
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MealMapperImplTest {

    private val context: Context = mockk()
    private lateinit var mapper: MealMapperImpl

    @Before
    fun setUp() {
        every { context.getString(R.string.meal_type_breakfast) } returns "Café da Manhã"
        every { context.getString(R.string.meal_type_lunch) } returns "Almoço"
        every { context.getString(R.string.meal_type_afternoon_snack) } returns "Lanche da Tarde"
        every { context.getString(R.string.meal_type_dinner) } returns "Jantar"
        every { context.getString(R.string.meal_type_breakfast_short) } returns "Café"
        every { context.getString(R.string.meal_type_afternoon_snack_short) } returns "Lanche"
        mapper = MealMapperImpl(context)
    }

    @Test
    fun `map BREAKFAST returns meal with correct type and labels`() {
        val meal = mapper.map(MealType.BREAKFAST)
        assertEquals(MealType.BREAKFAST, meal.type)
        assertEquals("Café da Manhã", meal.label)
        assertEquals("Café", meal.sortLabel)
    }

    @Test
    fun `map LUNCH returns meal with correct type and labels`() {
        val meal = mapper.map(MealType.LUNCH)
        assertEquals(MealType.LUNCH, meal.type)
        assertEquals("Almoço", meal.label)
        assertEquals("Almoço", meal.sortLabel)
    }

    @Test
    fun `map AFTERNOON_SNACK returns meal with correct type and labels`() {
        val meal = mapper.map(MealType.AFTERNOON_SNACK)
        assertEquals(MealType.AFTERNOON_SNACK, meal.type)
        assertEquals("Lanche da Tarde", meal.label)
        assertEquals("Lanche", meal.sortLabel)
    }

    @Test
    fun `map DINNER returns meal with correct type and labels`() {
        val meal = mapper.map(MealType.DINNER)
        assertEquals(MealType.DINNER, meal.type)
        assertEquals("Jantar", meal.label)
        assertEquals("Jantar", meal.sortLabel)
    }
}
