package com.teka.weatherapp.domain.usecase.location

import com.teka.weatherapp.data.location.DefaultLocationTracker
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(private val defaultLocationTracker: DefaultLocationTracker) {
    suspend fun getLocation() = defaultLocationTracker.getCurrentLocation()
}