package com.example.nutrichief.datamodels

data class Ingredient(
    var ingre_id: Int,
    var ingre_name: String,
    var ingre_price: Float,
    var ingre_calo: Int,
    var ingre_fat: Float,
    var ingre_protein: Float,
    var ingre_carb: Float,
    var ingre_img: String,
    )

//`ingredient` (ingre_name, ingre_price, ingre_calo, ingre_fat, ingre_protein, ingre_carb)