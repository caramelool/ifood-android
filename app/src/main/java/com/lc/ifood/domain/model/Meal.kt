package com.lc.ifood.domain.model

import androidx.annotation.Keep
import androidx.annotation.StringRes
import com.lc.ifood.R

/**
 * Represents the four supported meal slots in the app.
 *
 * The `@Keep` annotation prevents R8/ProGuard from renaming the enum values, because their
 * string names are persisted as primary keys in the local database and sent to the backend API.
 */
@Keep
enum class MealType {
    BREAKFAST, LUNCH, AFTERNOON_SNACK, DINNER
}

/**
 * A meal type paired with the user's scheduled time for that meal.
 *
 * @property mealType identifies which meal this schedule applies to.
 * @property hour hour of day (0–23).
 * @property minute minute within the hour (0–59).
 */
data class MealSchedule(
    val mealType: MealType,
    val hour: Int,
    val minute: Int = 0
) {
    /** Returns the scheduled time formatted as `"HH:mm"` (zero-padded). */
    val time: String
        get() = "%02d:%02d".format(hour, minute)
}

/** Full display label string resource for this meal type (e.g. "Breakfast"). */
@get:StringRes
val MealType.labelId: Int
    get() = when (this) {
        MealType.BREAKFAST -> R.string.meal_type_breakfast
        MealType.LUNCH -> R.string.meal_type_lunch
        MealType.AFTERNOON_SNACK -> R.string.meal_type_afternoon_snack
        MealType.DINNER -> R.string.meal_type_dinner
    }

/** Abbreviated display label string resource for compact UI surfaces (e.g. chips). */
@get:StringRes
val MealType.sortLabelId: Int
    get() = when (this) {
        MealType.BREAKFAST -> R.string.meal_type_breakfast_short
        MealType.LUNCH -> R.string.meal_type_lunch
        MealType.AFTERNOON_SNACK -> R.string.meal_type_afternoon_snack_short
        MealType.DINNER -> R.string.meal_type_dinner
    }
