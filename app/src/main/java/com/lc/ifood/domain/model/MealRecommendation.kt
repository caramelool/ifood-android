package com.lc.ifood.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MealRecommendation(
    val mealType: MealType,
    val placeName: String,
    val placeAddress: String,
    val mealName: String,
    val mealDescription: String,
    val mealPrice: Double,
    val preferences: List<String>
) : Parcelable
