package com.lc.ifood.domain.mapper

import com.lc.ifood.domain.model.Meal
import com.lc.ifood.domain.model.MealType

interface MealMapper {
    fun map(type: MealType): Meal
}
