package com.teka.weatherapp.screens.home

import com.teka.weatherapp.domain.model.Forecast

sealed interface HomeForecastState {
    data class Success(val forecast: Forecast?, val isReadingCachedData: Boolean? = null, val lastUpdated: Long? = null): HomeForecastState
    data class Error(val errorMessage: String?): HomeForecastState

    object Loading: HomeForecastState
}