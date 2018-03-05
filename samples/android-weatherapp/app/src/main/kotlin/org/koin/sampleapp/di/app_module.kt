package org.koin.sampleapp.di

import org.koin.dsl.module.applicationContext
import org.koin.sampleapp.di.Context.WEATHER_LIST
import org.koin.sampleapp.di.Params.DETAIL_VIEW
import org.koin.sampleapp.di.Params.RESULT_VIEW
import org.koin.sampleapp.di.Params.SEARCH_VIEW
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.repository.WeatherRepositoryImpl
import org.koin.sampleapp.util.rx.ApplicationSchedulerProvider
import org.koin.sampleapp.util.rx.SchedulerProvider
import org.koin.sampleapp.view.detail.DetailContract
import org.koin.sampleapp.view.detail.DetailPresenter
import org.koin.sampleapp.view.result.ResultContract
import org.koin.sampleapp.view.result.ResultListContract
import org.koin.sampleapp.view.result.ResultListPresenter
import org.koin.sampleapp.view.result.ResultPresenter
import org.koin.sampleapp.view.search.SearchContract
import org.koin.sampleapp.view.search.SearchPresenter

val weatherModule = applicationContext {

    // Presenter for Search View
    factory { params -> SearchPresenter(get(), get(), params[SEARCH_VIEW]) as SearchContract.Presenter }

    // Presenters for Result View
    // custom context to hold both fragment presenter instances
    context(WEATHER_LIST) {
        bean { ResultPresenter() as ResultContract.Presenter }
        bean { params -> ResultListPresenter(get(), get(), get(), params[RESULT_VIEW]) as ResultListContract.Presenter }
    }

    // Presenter for Detail View
    factory { params -> DetailPresenter(get(), get(), params[DETAIL_VIEW]) as DetailContract.Presenter }

    // Weather Data Repository
    bean { WeatherRepositoryImpl(get()) as WeatherRepository }
}

val rxModule = applicationContext {
    // provided components
    bean { ApplicationSchedulerProvider() as SchedulerProvider }
}

object Context {
    const val WEATHER_LIST = "WEATHER_LIST"
}

object Params {
    const val SEARCH_VIEW = "SEARCH_VIEW"
    const val RESULT_VIEW = "RESULT_VIEW"
    const val DETAIL_VIEW = "DETAIL_VIEW"
}

// Gather all app modules
val weatherApp = listOf(weatherModule, rxModule)
