package com.teka.weatherapp.data.mapper


import com.teka.weatherapp.data.datasource.local.db.entity.CityEntity
import com.teka.weatherapp.data.datasource.local.db.entity.ForecastEntity
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

class ForecastEntityMapper @Inject constructor() {
    fun mapFromEntity(entityForecast: List<ForecastEntity>, entityCity: CityEntity): Forecast {
        return Forecast(
            entityForecast.map { it ->
                ForecastWeather(
                    it.id,
                    Main(
                        it.temp,
                        it.feels_like,
                        it.pressure,
                        it.humidity
                    ),
                    listOf(
                        Weather(it.mainDescription, it.description, it.icon)
                    ),
                    Wind(it.wind_speed),
                    it.date,
                    Cloudiness(it.cloudiness)
                )
            },
            City(
                entityCity.country,
                entityCity.timezone,
                entityCity.sunrise,
                entityCity.sunset,
                entityCity.cityName,
                Coord(
                    entityCity.longitude,
                    entityCity.latitude
                )
            ),
            lastUpdated = entityForecast[0].lastUpdated
        )
    }

    fun entityFromModel(model: Forecast): ForecastEntity {
        return ForecastEntity(
            id = model.weatherList[0].id,
            temp = model.weatherList[0].weatherData.temp,
            feels_like = model.weatherList[0].weatherData.feelsLike,
            pressure = model.weatherList[0].weatherData.pressure,
            humidity = model.weatherList[0].weatherData.humidity,
            wind_speed = model.weatherList[0].wind.speed,
            description = model.weatherList[0].weatherStatus[0].description,
            mainDescription = model.weatherList[0].weatherStatus[0].mainDescription,
            date = model.weatherList[0].date,
            cloudiness = model.weatherList[0].cloudiness.cloudiness,
            icon = model.weatherList[0].weatherStatus[0].icon,
            lastUpdated = model.lastUpdated,
        )
    }
}