package org.koin.sampleapp.di

import org.koin.dsl.module.applicationContext
import org.koin.sampleapp.di.Context.WEATHER_LIST
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.repository.WeatherRepositoryImpl
import org.koin.sampleapp.util.rx.ApplicationSchedulerProvider
import org.koin.sampleapp.util.rx.SchedulerProvider
import org.koin.sampleapp.view.detail.WeatherDetailContract
import org.koin.sampleapp.view.detail.WeatherDetailPresenter
import org.koin.sampleapp.view.main.MainContract
import org.koin.sampleapp.view.main.MainPresenter
import org.koin.sampleapp.view.weather.WeatherListResultContract
import org.koin.sampleapp.view.weather.WeatherResultContract
import org.koin.sampleapp.view.weather.WeatherListResultPresenter
import org.koin.sampleapp.view.weather.WeatherResultPresenter


val weatherModule = applicationContext {

    factory { MainPresenter(get(), get()) as MainContract.Presenter }

    context(WEATHER_LIST) {
        bean { WeatherResultPresenter() as WeatherResultContract.Presenter }
        bean { WeatherListResultPresenter(get(), get(), get()) as WeatherListResultContract.Presenter }
    }

    factory { WeatherDetailPresenter(get(), get()) as WeatherDetailContract.Presenter }

    bean { WeatherRepositoryImpl(get()) as WeatherRepository }
}

val rxModule = applicationContext {
    // provided components
    bean { ApplicationSchedulerProvider() as SchedulerProvider }
}

object Context {
    const val WEATHER_LIST = "WEATHER_LIST"
}

object WeatherAppProperties {
    const val PROPERTY_ADDRESS: String = "PROPERTY_ADDRESS"
    const val PROPERTY_WEATHER_DATE = "WEATHER_DATE"
    const val PROPERTY_WEATHER_ITEM_ID: String = "WEATHER_ID"
}

// Gather all app modules
val weatherApp = listOf(weatherModule, rxModule)
