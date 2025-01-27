package com.teka.weatherapp.domain.usecase.my_city

import com.teka.weatherapp.data.repository.MyCityRepositoryImpl
import com.teka.weatherapp.domain.model.MyCity
import javax.inject.Inject

class UpdateMyCityUseCase @Inject constructor(private val myCityRepositoryImpl: MyCityRepositoryImpl) {
    suspend fun updateMyCityUseCase(myCity: MyCity) = myCityRepositoryImpl.updateMyCity(myCity)
}