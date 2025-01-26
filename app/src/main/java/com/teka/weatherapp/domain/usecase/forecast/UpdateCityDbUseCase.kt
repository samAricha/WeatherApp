package com.teka.weatherapp.domain.usecase.forecast

import com.teka.weatherapp.data.repository.ForecastRepositoryImpl
import com.teka.weatherapp.domain.model.City
import javax.inject.Inject

class UpdateCityDbUseCase @Inject constructor(private val forecastRepositoryImpl: ForecastRepositoryImpl) {
    suspend fun updateCityDb(city: City) = forecastRepositoryImpl.updateCity(city)
}