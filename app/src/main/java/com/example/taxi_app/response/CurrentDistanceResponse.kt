package com.example.taxi_app.response


import com.google.gson.annotations.SerializedName

data class CurrentDistanceResponse(
    @SerializedName("destination_addresses")
    val destinationAddresses: List<String>,
    @SerializedName("origin_addresses")
    val originAddresses: List<String>,
    @SerializedName("rows")
    val rows: List<Row>,
    @SerializedName("status")
    val status: String
)