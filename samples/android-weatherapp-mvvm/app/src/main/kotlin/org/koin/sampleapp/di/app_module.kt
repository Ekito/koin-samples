package org.koin.sampleapp.di

import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.repository.WeatherRepositoryImpl
import org.koin.sampleapp.util.rx.ApplicationSchedulerProvider
import org.koin.sampleapp.util.rx.SchedulerProvider
import org.koin.sampleapp.view.detail.WeatherDetailViewModel
import org.koin.sampleapp.view.main.MainViewModel
import org.koin.sampleapp.view.weather.WeatherResultViewModel


val weatherModule = applicationContext {

    viewModel { MainViewModel(get(), get()) }

    viewModel { WeatherResultViewModel(get(), get()) }

    viewModel { WeatherDetailViewModel(get(), get()) }

    bean { WeatherRepositoryImpl(get()) as WeatherRepository }
}

val rxModule = applicationContext {
    // provided components
    bean { ApplicationSchedulerProvider() as SchedulerProvider }
}

object WeatherAppProperties {
    const val PROPERTY_ADDRESS: String = "PROPERTY_ADDRESS"
    const val PROPERTY_WEATHER_DATE = "WEATHER_DATE"
}

// Gather all app modules
val weatherApp = listOf(weatherModule, rxModule)
