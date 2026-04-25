package com.lc.ifood.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal_schedules")
data class MealScheduleEntity(
    @PrimaryKey val mealType: String,
    val label: String,
    val hour: Int,
    val minute: Int
)
