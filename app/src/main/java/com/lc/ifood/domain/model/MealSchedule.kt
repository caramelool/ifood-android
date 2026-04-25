package com.lc.ifood.domain.model

data class MealSchedule(
    val mealType: MealType,
    val label: String,
    val hour: Int,
    val minute: Int = 0
)

fun MealSchedule.time(): String {
    return "%02d:%02d".format(hour, minute)
}

fun MealSchedule.period(): String {
    return if (hour < 12) "am" else "pm"
}
