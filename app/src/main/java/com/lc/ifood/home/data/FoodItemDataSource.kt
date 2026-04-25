package com.lc.ifood.home.data

import com.lc.ifood.core.domain.model.FoodItem
import com.lc.ifood.core.domain.model.MealType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FoodItemDataSource @Inject constructor() {

    fun getAll(): List<FoodItem> = listOf(
        FoodItem(1, "Pão de Queijo", "Fresquinho e crocante", 8.90, MealType.BREAKFAST),
        FoodItem(2, "Café Americano", "Coado na hora", 6.50, MealType.BREAKFAST),
        FoodItem(3, "Tapioca", "Com queijo e presunto", 12.90, MealType.BREAKFAST),
        FoodItem(4, "Vitamina de Frutas", "Morango, banana e manga", 14.00, MealType.BREAKFAST),
        FoodItem(5, "Frango Grelhado", "Com arroz, feijão e salada", 29.90, MealType.LUNCH),
        FoodItem(6, "Salada Caesar", "Alface, croutons e parmesão", 24.90, MealType.LUNCH),
        FoodItem(7, "Marmita Executiva", "Prato do dia completo", 22.00, MealType.LUNCH),
        FoodItem(8, "Lasanha Bolonhesa", "Massa fresca, molho e queijo", 35.90, MealType.LUNCH),
        FoodItem(9, "Coxinha", "Frango com catupiry", 9.90, MealType.AFTERNOON_SNACK),
        FoodItem(10, "Suco Natural", "Laranja ou limão espremido", 11.00, MealType.AFTERNOON_SNACK),
        FoodItem(11, "Pastel de Queijo", "Crocante por fora, derretido por dentro", 8.50, MealType.AFTERNOON_SNACK),
        FoodItem(12, "Açaí 300ml", "Com granola e banana", 19.90, MealType.AFTERNOON_SNACK),
        FoodItem(13, "Pizza Margherita", "Molho, mussarela e manjericão", 45.90, MealType.DINNER),
        FoodItem(14, "Hambúrguer Artesanal", "180g de carne, queijo e salada", 38.90, MealType.DINNER),
        FoodItem(15, "Salmão Grelhado", "Com legumes e arroz integral", 52.00, MealType.DINNER),
        FoodItem(16, "Risoto de Camarão", "Cremoso com camarões frescos", 48.90, MealType.DINNER)
    )
}
