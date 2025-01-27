package com.teka.weatherapp.domain.model

data class Forecast(
    val weatherList: List<ForecastWeather>,
    val cityDtoData: City,
    val lastUpdated: Long
)
