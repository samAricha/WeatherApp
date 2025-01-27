package com.teka.weatherapp.screens.search

import com.teka.weatherapp.domain.model.MyCity

sealed interface MyCitiesState {
    data class Success(val forecast: List<MyCity>?): MyCitiesState
    data class Error(val errorMessage: String?): MyCitiesState

    object Loading: MyCitiesState
}