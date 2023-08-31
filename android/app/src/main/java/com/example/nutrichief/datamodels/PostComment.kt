package com.example.nutrichief.datamodels

data class PostComment(
    val post_id: Int,
    val comment_id: Int,
    val user_name: String,
    val comment_detail: String,
)

