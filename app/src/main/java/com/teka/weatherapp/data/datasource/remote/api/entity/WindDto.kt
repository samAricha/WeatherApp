package com.teka.weatherapp.data.datasource.remote.api.entity

import com.google.gson.annotations.SerializedName

data class WindDto(
    @SerializedName("speed") val speed: Double,
)
