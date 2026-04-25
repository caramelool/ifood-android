package com.lc.ifood.core.data.db.converter

import androidx.room.TypeConverter
import com.lc.ifood.core.domain.model.MealType

class MealTypeConverter {

    @TypeConverter
    fun fromMealTypeList(mealTypes: List<MealType>): String =
        mealTypes.joinToString(",") { it.name }

    @TypeConverter
    fun toMealTypeList(value: String): List<MealType> =
        if (value.isBlank()) emptyList()
        else value.split(",").mapNotNull { runCatching { MealType.valueOf(it) }.getOrNull() }
}
