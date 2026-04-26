package com.lc.ifood.domain.factory

import com.lc.ifood.domain.model.Meal
import com.lc.ifood.domain.model.MealType

interface MealFactory {
    fun factoryMeal(type: MealType): Meal
}
