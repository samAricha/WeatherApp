package com.teka.weatherapp.domain.usecase.forecast

import com.teka.weatherapp.data.repository.ForecastRepositoryImpl
import com.teka.weatherapp.domain.model.Forecast
import javax.inject.Inject

class GetForecastFromDbUseCase @Inject constructor(private val forecastRepositoryImpl: ForecastRepositoryImpl) {
    fun getForecastFromDbUseCase() : Forecast? = forecastRepositoryImpl.getForecastWeather()
}