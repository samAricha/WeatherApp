package com.teka.weatherapp.domain.repository

import com.teka.weatherapp.domain.model.MyCity

interface MyCityRepository {
    suspend fun addMyCity(myCity: MyCity)

    fun getMyCity() : List<MyCity>

    fun deleteMyCity(cityName: String)

    suspend fun updateMyCity(myCity: MyCity)

    suspend fun getSpecificCity(cityName: String) : Boolean
}