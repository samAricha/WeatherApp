package com.teka.weatherapp.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teka.weatherapp.domain.model.City
import com.teka.weatherapp.domain.model.Forecast
import com.teka.weatherapp.domain.usecase.forecast.AddCityToDbUseCase
import com.teka.weatherapp.domain.usecase.forecast.AddForecastToDbUseCase
import com.teka.weatherapp.domain.usecase.forecast.GetForecastFromDbUseCase
import com.teka.weatherapp.domain.usecase.forecast.GetForecastUseCase
import com.teka.weatherapp.domain.usecase.forecast.UpdateCityDbUseCase
import com.teka.weatherapp.domain.usecase.forecast.UpdateForecastDbUseCase
import com.teka.weatherapp.domain.usecase.location.GetLocationUseCase
import com.teka.weatherapp.utils.ExceptionTitles
import com.teka.weatherapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val addForecastDb: AddForecastToDbUseCase,
    private val addCityDb: AddCityToDbUseCase,
    private val updateCityDbUseCase: UpdateCityDbUseCase,
    private val getForecastDb: GetForecastFromDbUseCase,
    private val updateForecastDb: UpdateForecastDbUseCase,
    private val getForecast: GetForecastUseCase,
    private val getCurrentLocation: GetLocationUseCase
) : ViewModel() {
    val timber = Timber.tag("HomeViewModel")

    private val _homeForecastState = MutableStateFlow<HomeForecastState>(HomeForecastState.Loading)
    val homeForecastState = _homeForecastState.asStateFlow()

    fun loadLocation() {
        timber.i("Loading Location info")

        _homeForecastState.value = HomeForecastState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val locationData = getCurrentLocation.getLocation()
                if (locationData != null) {
                    fetchForecast(locationData.latitude, locationData.longitude)
                } else if (isForecastCached()) {
                    getCachedForecast()
                } else {
                    _homeForecastState.value = HomeForecastState.Error(ExceptionTitles.NO_INTERNET_CONNECTION)
                }
            } catch (e: Exception) {
                if (isForecastCached()) {
                    getCachedForecast()
                } else {
                    _homeForecastState.value = HomeForecastState.Error(e.message)
                }
            }
        }
    }

    private suspend fun fetchForecast(latitude: Double, longitude: Double) {
        timber.i("Fetching ForeCast info")

        when (val result: Resource<Forecast> = getForecast.getForecast(latitude, longitude)) {
            is Resource.Success -> {
                _homeForecastState.value = HomeForecastState.Success(result.data)
                if (result.data != null) {
                    if (!isForecastCached()) {
                        cacheForecast(result.data, result.data.cityDtoData)
                    } else {
                        updateCachedForecast(result.data, result.data.cityDtoData)
                    }
                }
            }
            is Resource.Error -> {
                if (isForecastCached()) {
                    getCachedForecast()
                } else {
                    _homeForecastState.value = HomeForecastState.Error(result.message)
                }
            }
        }
    }

    private suspend fun cacheForecast(forecast: Forecast, city: City) {
        addForecastDb.addForecastToDbUseCase(
            forecast,
            forecast.weatherList.size
        )
        addCityDb.addCityDb(city)
    }

    private suspend fun updateCachedForecast(forecast: Forecast, city: City) {
        updateForecastDb.updateForecastDbUseCase(
            forecast,
            forecast.weatherList.size
        )
        updateCityDbUseCase.updateCityDb(city)
    }

    // Data cannot be null.
    // Because before this function is called, it is checked for null with the isForecastCached() function.
    private fun getCachedForecast() {
        val data = getForecastDb.getForecastFromDbUseCase()
        _homeForecastState.value =
            HomeForecastState.Success(
                data,
                isReadingCachedData = true,
                data?.lastUpdated ?: System.currentTimeMillis()
            )
    }

    private fun isForecastCached(): Boolean {
        val isForecastCached = getForecastDb.getForecastFromDbUseCase() != null
        timber.i("isForecastDataCached $isForecastCached")
        return isForecastCached
    }
}