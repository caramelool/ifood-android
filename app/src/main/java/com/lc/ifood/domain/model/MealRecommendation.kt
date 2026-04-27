package com.lc.ifood.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * A meal recommendation returned by the backend for a given meal type and user preferences.
 *
 * Implements [Parcelable] so it can be passed from the notification [android.app.PendingIntent]
 * to [com.lc.ifood.MainActivity] via an [android.content.Intent] extra without serialization overhead.
 *
 * @property mealType the meal slot this recommendation belongs to.
 * @property placeName restaurant or establishment name.
 * @property placeAddress street address of the establishment.
 * @property mealName name of the recommended dish.
 * @property mealDescription short description of the dish.
 * @property mealPrice price in BRL.
 * @property preferences dietary tags that influenced this recommendation.
 * @property mealImageUrl an illustrative image URL of the recommended dish.
 */
@Parcelize
data class MealRecommendation(
    val mealType: MealType,
    val placeName: String,
    val placeAddress: String,
    val mealName: String,
    val mealDescription: String,
    val mealPrice: Double,
    val preferences: List<String>,
    val mealImageUrl: String
) : Parcelable
