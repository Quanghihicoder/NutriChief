package com.example.nutrichief.datamodels

data class CommunityPost(
    var post_id: Int,
    var post_title: String,
    var post_detail: String,
    var user_name: String,
    var post_like: Int,
    var post_dislike: Int
)
