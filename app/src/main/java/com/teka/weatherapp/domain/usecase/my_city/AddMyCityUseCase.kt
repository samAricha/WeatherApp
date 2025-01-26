package com.teka.weatherapp.domain.usecase.my_city

import com.teka.weatherapp.data.repository.MyCityRepositoryImpl
import com.teka.weatherapp.domain.model.MyCity
import javax.inject.Inject

class AddMyCityUseCase @Inject constructor(private val myCityRepositoryImpl: MyCityRepositoryImpl) {
    suspend fun addMyCity(myCity: MyCity) = myCityRepositoryImpl.addMyCity(myCity)
}