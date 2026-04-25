package com.lc.ifood.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lc.ifood.R
import com.lc.ifood.core.domain.model.MealType

@Composable
fun MealType.toLabel(): String = when (this) {
    MealType.BREAKFAST -> stringResource(R.string.meal_type_breakfast)
    MealType.LUNCH -> stringResource(R.string.meal_type_lunch)
    MealType.AFTERNOON_SNACK -> stringResource(R.string.meal_type_afternoon_snack)
    MealType.DINNER -> stringResource(R.string.meal_type_dinner)
}

@Composable
fun MealType.toShortLabel(): String = when (this) {
    MealType.BREAKFAST -> stringResource(R.string.meal_type_breakfast_short)
    MealType.LUNCH -> stringResource(R.string.meal_type_lunch)
    MealType.AFTERNOON_SNACK -> stringResource(R.string.meal_type_afternoon_snack_short)
    MealType.DINNER -> stringResource(R.string.meal_type_dinner)
}
