package com.lc.ifood.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_preferences")
data class UserPreferenceEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val label: String,
    val mealTypes: String
)
