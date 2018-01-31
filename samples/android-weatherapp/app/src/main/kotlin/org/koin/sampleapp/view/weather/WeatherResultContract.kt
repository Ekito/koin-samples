package org.koin.sampleapp.view.weather

import org.koin.sampleapp.model.DailyForecastModel
import org.koin.sampleapp.util.mvp.BasePresenter
import org.koin.sampleapp.util.mvp.BaseView

/**
 * Weather MVP Contract
 */
interface WeatherResultContract {
    interface View : BaseView<Presenter> {
        fun displayWeather(weatherList: List<DailyForecastModel>)
        fun onDetailSaved()
        fun displayError(error: Throwable)
    }

    interface Presenter : BasePresenter<View> {
        fun getWeather()
        fun selectWeatherDetail(detail: DailyForecastModel)
    }
}

const val PROPERTY_WEATHER_DATE = "WEATHER_DATE"

