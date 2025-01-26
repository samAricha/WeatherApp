package com.teka.weatherapp.data.datasource.local.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.teka.weatherapp.data.datasource.local.db.entity.CityEntity
import com.teka.weatherapp.data.datasource.local.db.entity.ForecastEntity
import com.teka.weatherapp.data.datasource.local.db.entity.MyCityEntity
import com.teka.weatherapp.data.datasource.local.db.room.CityDao
import com.teka.weatherapp.data.datasource.local.db.room.ForecastDao
import com.teka.weatherapp.data.datasource.local.db.room.MyCityDao

@Database(entities = [CityEntity::class, ForecastEntity::class, MyCityEntity::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao

    abstract fun forecastWeatherDao(): ForecastDao

    abstract fun myCityDao(): MyCityDao
}