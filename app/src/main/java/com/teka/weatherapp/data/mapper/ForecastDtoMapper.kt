package com.teka.weatherapp.data.mapper

import com.teka.weatherapp.data.datasource.remote.api.entity.CityDto
import com.teka.weatherapp.data.datasource.remote.api.entity.CloudinessDto
import com.teka.weatherapp.data.datasource.remote.api.entity.CoordDto
import com.teka.weatherapp.domain.mapper.IEntityMapper
import com.teka.weatherapp.domain.model.*
import com.teka.weatherapp.data.datasource.remote.api.entity.ForecastDto
import com.teka.weatherapp.data.datasource.remote.api.entity.ForecastWeatherDto
import com.teka.weatherapp.data.datasource.remote.api.entity.MainDto
import com.teka.weatherapp.data.datasource.remote.api.entity.WeatherDto
import com.teka.weatherapp.data.datasource.remote.api.entity.WindDto
import com.teka.weatherapp.domain.model.City
import com.teka.weatherapp.domain.model.Cloudiness
import com.teka.weatherapp.domain.model.Coord
import com.teka.weatherapp.domain.model.Forecast
import com.teka.weatherapp.domain.model.ForecastWeather
import com.teka.weatherapp.domain.model.Main
import com.teka.weatherapp.domain.model.Weather
import com.teka.weatherapp.domain.model.Wind
import javax.inject.Inject
import kotlin.collections.get

class ForecastDtoMapper @Inject constructor() : IEntityMapper<ForecastDto, Forecast> {
    override fun mapFromEntity(entity: ForecastDto): Forecast {
        val forecastWeather: List<ForecastWeather> = entity.weatherList.map {
            ForecastWeather(
                weatherData = Main(
                    it.weatherData.temp,
                    it.weatherData.feelsLike,
                    it.weatherData.pressure,
                    it.weatherData.humidity
                ),
                weatherStatus = listOf(
                    Weather(it.weatherStatus[0].mainDescription, it.weatherStatus[0].description, it.weatherStatus[0].icon)
                ),
                wind = Wind(it.wind.speed),
                date = it.date,
                cloudiness = Cloudiness(it.cloudinessDto.cloudiness)
            )
        }

        return Forecast(
            forecastWeather,
            City(
                entity.cityDtoData.country,
                entity.cityDtoData.timezone,
                entity.cityDtoData.sunrise,
                entity.cityDtoData.sunset,
                entity.cityDtoData.cityName,
                Coord(
                    entity.cityDtoData.coordinate.latitude,
                    entity.cityDtoData.coordinate.longitude
                )
            )
        )
    }

    override fun entityFromModel(model: Forecast): ForecastDto {
        val forecastWeatherDto: List<ForecastWeatherDto> = model.weatherList.map {
            ForecastWeatherDto(
                MainDto(
                    it.weatherData.temp,
                    it.weatherData.feelsLike,
                    it.weatherData.pressure,
                    it.weatherData.humidity
                ),
                listOf(
                    WeatherDto(it.weatherStatus[0].mainDescription, it.weatherStatus[0].description, it.weatherStatus[0].icon)
                ),
                WindDto(it.wind.speed),
                it.date,
                CloudinessDto(it.cloudiness.cloudiness)
            )
        }

        return ForecastDto(
            forecastWeatherDto,
            CityDto(
                model.cityDtoData.country,
                model.cityDtoData.timezone,
                model.cityDtoData.sunrise,
                model.cityDtoData.sunset,
                model.cityDtoData.cityName,
                CoordDto(
                    model.cityDtoData.coordinate.latitude,
                    model.cityDtoData.coordinate.longitude
                )
            )
        )
    }
}