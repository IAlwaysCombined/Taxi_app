package com.example.taxi_app.response


import com.google.gson.annotations.SerializedName

data class Element(
    @SerializedName("distance")
    val distance: Distance,
    @SerializedName("duration")
    val duration: Duration,
    @SerializedName("status")
    val status: String
)