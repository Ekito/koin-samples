package org.koin.sampleapp.di

import org.koin.dsl.module.applicationContext
import org.koin.sampleapp.di.Context.WEATHER_LIST
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.repository.WeatherRepositoryImpl
import org.koin.sampleapp.util.rx.ApplicationSchedulerProvider
import org.koin.sampleapp.util.rx.SchedulerProvider
import org.koin.sampleapp.view.detail.DetailContract
import org.koin.sampleapp.view.detail.DetailPresenter
import org.koin.sampleapp.view.search.SearchContract
import org.koin.sampleapp.view.search.SearchPresenter
import org.koin.sampleapp.view.result.ResultListContract
import org.koin.sampleapp.view.result.ResultContract
import org.koin.sampleapp.view.result.ResultListPresenter
import org.koin.sampleapp.view.result.ResultPresenter


val weatherModule = applicationContext {

    factory { SearchPresenter(get(), get()) as SearchContract.Presenter }

    // custom context to hold both presenter instances
    context(WEATHER_LIST) {
        bean { ResultPresenter() as ResultContract.Presenter }
        bean { ResultListPresenter(get(), get(), get()) as ResultListContract.Presenter }
    }

    factory { params -> DetailPresenter(get(), get(), params["activity"]) as DetailContract.Presenter }

    bean { WeatherRepositoryImpl(get()) as WeatherRepository }
}

val rxModule = applicationContext {
    // provided components
    bean { ApplicationSchedulerProvider() as SchedulerProvider }
}

object Context {
    const val WEATHER_LIST = "WEATHER_LIST"
}

// Gather all app modules
val weatherApp = listOf(weatherModule, rxModule)
