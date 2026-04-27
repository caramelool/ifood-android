package com.lc.ifood.domain.model

import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.lc.ifood.R

@Keep
enum class MealType {
    BREAKFAST, LUNCH, AFTERNOON_SNACK, DINNER
}

data class MealSchedule(
    val mealType: MealType,
    val hour: Int,
    val minute: Int = 0
) {
    val time: String
        get() = "%02d:%02d".format(hour, minute)
}

@get:StringRes
val MealType.labelId: Int
    get() = when (this) {
        MealType.BREAKFAST -> R.string.meal_type_breakfast
        MealType.LUNCH -> R.string.meal_type_lunch
        MealType.AFTERNOON_SNACK -> R.string.meal_type_afternoon_snack
        MealType.DINNER -> R.string.meal_type_dinner
    }

@get:StringRes
val MealType.sortLabelId: Int
    get() = when (this) {
        MealType.BREAKFAST -> R.string.meal_type_breakfast_short
        MealType.LUNCH -> R.string.meal_type_lunch
        MealType.AFTERNOON_SNACK -> R.string.meal_type_afternoon_snack_short
        MealType.DINNER -> R.string.meal_type_dinner
    }
