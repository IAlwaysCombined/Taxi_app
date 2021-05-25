package com.example.taxi_app.models

data class DriverModel(

    val uid: String = "",
    val phone_driver: String = "",
    var name_driver: String = "",
    var email_driver: String = "",
    var pay_method: String = "",
    val car_number: String = "",
    val car: String = "",
)
