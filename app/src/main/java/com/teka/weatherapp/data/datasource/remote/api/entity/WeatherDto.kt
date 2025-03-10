package com.teka.weatherapp.data.datasource.remote.api.entity

import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("main") val mainDescription: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)
