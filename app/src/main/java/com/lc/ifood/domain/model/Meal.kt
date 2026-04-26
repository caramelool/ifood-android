package com.lc.ifood.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meal(
    val type: MealType,
    val label: String,
    val sortLabel: String
) : Parcelable

@Keep
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
