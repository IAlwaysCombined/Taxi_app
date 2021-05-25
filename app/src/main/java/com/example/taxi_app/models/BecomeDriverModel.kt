package com.example.taxi_app.models

data class BecomeDriverModel(
    var uid: String = "",
    val car: String = "",
    var car_number: String = "",
    var name_driver: String = "",
    var surname_driver: String = "",
    var last_name_driver: String = "",
    var phone_number_driver: String = "",
    var photo_driver: String = "",
    var photo_car: String = "",
    var photo_licence: String = "",
    var email_driver: String = "",
)