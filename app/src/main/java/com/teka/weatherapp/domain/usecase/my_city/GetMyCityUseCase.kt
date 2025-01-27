package com.teka.weatherapp.domain.usecase.my_city

import com.teka.weatherapp.data.repository.MyCityRepositoryImpl
import javax.inject.Inject

class GetMyCityUseCase @Inject constructor(private val myCityRepositoryImpl: MyCityRepositoryImpl) {
    fun getMyCity() = myCityRepositoryImpl.getMyCity()
}