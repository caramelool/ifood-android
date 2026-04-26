package com.lc.ifood.domain.model

data class Meal(
    val type: MealType,
    val label: String,
    val sortLabel: String
)

enum class MealType {
    BREAKFAST, LUNCH, AFTERNOON_SNACK, DINNER
}

data class MealSchedule(
    val meal: Meal,
    val hour: Int,
    val minute: Int = 0
) {
    val time: String
        get() = "%02d:%02d".format(hour, minute)
}
