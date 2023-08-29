package com.example.nutrichief.datamodels

data class RecipeIngredient(
    val food_id: Int,
    val ingredient: Ingredient,
    val recipe_qty: Float,
    val recipe_title: String,
    val recipe_desc: String,
    val recipe_price: Float,
    val recipe_calories: Float,
    val recipe_carb: Float,
    val recipe_fat: Float,
    val recipe_protein: Float,
)
