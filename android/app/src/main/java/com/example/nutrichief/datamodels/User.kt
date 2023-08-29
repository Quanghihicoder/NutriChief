package com.example.nutrichief.datamodels

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class User(
    var user_id: Int?,
    var user_name: String?,
    var user_email: String?,
    var user_year_of_birth: Int?,
    var user_gender: Int?,
    var user_height: Float?,
    var user_weight: Float?,
    var user_activity_level: Int?,
    var user_bmi: Float?,
    var user_tdee: Float?
)