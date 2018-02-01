package org.koin.sampleapp.di

import org.koin.dsl.module.applicationContext
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.repository.WeatherRepositoryImpl
import org.koin.sampleapp.util.rx.ApplicationSchedulerProvider
import org.koin.sampleapp.util.rx.SchedulerProvider
import org.koin.sampleapp.view.detail.WeatherDetailContract
import org.koin.sampleapp.view.detail.WeatherDetailPresenter
import org.koin.sampleapp.view.main.MainContract
import org.koin.sampleapp.view.main.MainPresenter
import org.koin.sampleapp.view.weather.WeatherResultContract
import org.koin.sampleapp.view.weather.WeatherResultPresenter


val weatherModule = applicationContext {

    factory { MainPresenter(get(), get()) as MainContract.Presenter }

    factory { WeatherResultPresenter(get(), get()) as WeatherResultContract.Presenter }

    factory { WeatherDetailPresenter(get(), get()) as WeatherDetailContract.Presenter }

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
