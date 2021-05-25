package com.example.taxi_app.response


import com.google.gson.annotations.SerializedName

data class Row(
    @SerializedName("elements")
    val elements: List<Element>
)