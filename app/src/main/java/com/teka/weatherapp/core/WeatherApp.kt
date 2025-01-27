package com.teka.weatherapp.core

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class WeatherApp: Application(){
    override fun onCreate() {
        super.onCreate()
//        if (BuildConfig.DEBUG) {
            Timber.Forest.plant(Timber.DebugTree())
//        }
    }
}