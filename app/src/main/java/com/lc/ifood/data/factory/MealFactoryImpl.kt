package com.lc.ifood.data.factory

import android.content.Context
import com.lc.ifood.R
import com.lc.ifood.domain.factory.MealFactory
import com.lc.ifood.domain.model.Meal
import com.lc.ifood.domain.model.MealType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MealFactoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : MealFactory {

    override fun factoryMeal(type: MealType): Meal {
        return Meal(
            type = type,
            label = getLabel(type),
            sortLabel = getShortLabel(type)
        )
    }

    private fun getLabel(type: MealType): String = when (type) {
        MealType.BREAKFAST -> context.getString(R.string.meal_type_breakfast)
        MealType.LUNCH -> context.getString(R.string.meal_type_lunch)
        MealType.AFTERNOON_SNACK -> context.getString(R.string.meal_type_afternoon_snack)
        MealType.DINNER -> context.getString(R.string.meal_type_dinner)
    }

    private fun getShortLabel(type: MealType): String = when (type) {
        MealType.BREAKFAST -> context.getString(R.string.meal_type_breakfast_short)
        MealType.LUNCH -> context.getString(R.string.meal_type_lunch)
        MealType.AFTERNOON_SNACK -> context.getString(R.string.meal_type_afternoon_snack_short)
        MealType.DINNER -> context.getString(R.string.meal_type_dinner)
    }
}
