package com.example.nutrichief.datamodels

data class CartItem(
    var itemID: Int,
    var imageUrl: String,
    var itemName: String,
    var itemPrice: Float,
    var itemDesc: String,
    var quantity: Int,
    var foodID: Int
)
