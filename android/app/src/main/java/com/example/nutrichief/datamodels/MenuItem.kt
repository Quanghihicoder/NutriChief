package com.example.nutrichief.datamodels

data class MenuItem(
    val food_id: Int,
    val food_name: String,
    val food_desc: String,
    val food_ctime: Int,
    val food_ptime: Int,
    val food_img: String,
    val food_price: Float,
    var food_quantity: Int = 0
)
