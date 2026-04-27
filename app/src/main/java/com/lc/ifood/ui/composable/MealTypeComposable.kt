package com.lc.ifood.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.lc.ifood.domain.model.MealType
import com.lc.ifood.domain.model.labelId
import com.lc.ifood.domain.model.sortLabelId

interface MealTypeComposable {
    val label: String
    val sortLabel: String
}

@Composable
fun MealType.composable() = object : MealTypeComposable {
    override val label: String = stringResource(labelId)
    override val sortLabel: String = stringResource(sortLabelId)
}
