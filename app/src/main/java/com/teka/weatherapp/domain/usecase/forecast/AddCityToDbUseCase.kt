package com.teka.weatherapp.domain.usecase.forecast


import com.teka.weatherapp.data.repository.ForecastRepositoryImpl
import com.teka.weatherapp.domain.model.City
import javax.inject.Inject

class AddCityToDbUseCase @Inject constructor(private val forecastRepositoryImpl: ForecastRepositoryImpl) {
    suspend fun addCityDb(city: City) = forecastRepositoryImpl.addCity(city)
}