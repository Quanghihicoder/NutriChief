package com.example.nutrichief.datamodels

data class RecipeIngredient(
    val food_id: Int,
    val ingredient: Ingredient,
    val recipeQty: Float,

    val recipeTitle: String,
    val recipeDesc: String,
    )
