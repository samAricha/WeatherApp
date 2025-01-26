package com.teka.weatherapp.domain.usecase.my_city

import com.teka.weatherapp.data.repository.MyCityRepositoryImpl
import javax.inject.Inject

class GetSpecificCityUseCase @Inject constructor(private val myCityRepositoryImpl: MyCityRepositoryImpl) {
    suspend fun getSpecificCityUseCase(cityName: String) =
        myCityRepositoryImpl.getSpecificCity(cityName)
}