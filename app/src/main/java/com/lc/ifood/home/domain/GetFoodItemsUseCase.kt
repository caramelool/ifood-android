package com.lc.ifood.home.domain

import com.lc.ifood.core.domain.model.FoodItem
import com.lc.ifood.home.data.FoodItemDataSource
import javax.inject.Inject

class GetFoodItemsUseCase @Inject constructor(
    private val dataSource: FoodItemDataSource
) {
    operator fun invoke(): List<FoodItem> = dataSource.getAll()
}
