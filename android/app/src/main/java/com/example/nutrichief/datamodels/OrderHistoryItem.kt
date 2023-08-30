package com.example.nutrichief.datamodels

// Order History data model
data class OrderHistoryItem(
    var date: String = "DATE",
    var orderId: String = "ORDER_ID",
    var orderStatus: String = "ORDER_STATUS",
    var orderPayment: String = "ORDER_PAYMENT",
    var price: String = "ORDER_PRICE",
)
