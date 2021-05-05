package com.example.taxi_app.models

data class User(
    val id: String = "",
    val role: String = "",
    val phone_user: String = "",
    var name_user: String = "",
    var email_user: String = "",
)