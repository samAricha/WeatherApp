package com.teka.weatherapp.domain.usecase.forecast


import com.teka.weatherapp.data.repository.ForecastRepositoryImpl
import com.teka.weatherapp.domain.model.Forecast
import com.teka.weatherapp.utils.Resource
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(private val forecastRepositoryImpl: ForecastRepositoryImpl) {
    suspend fun getForecast(latitude: Double, longitude: Double): Resource<Forecast> =
        forecastRepositoryImpl.getForecastData(latitude, longitude)
}