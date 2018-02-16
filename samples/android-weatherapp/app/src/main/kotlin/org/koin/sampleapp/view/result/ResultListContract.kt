package org.koin.sampleapp.view.result

import org.koin.sampleapp.model.DailyForecastModel
import org.koin.sampleapp.util.mvp.BasePresenter
import org.koin.sampleapp.util.mvp.BaseView

interface ResultListContract {
    interface View : BaseView<Presenter> {
        fun displayWeather(weatherList: List<DailyForecastModel>)
        fun displayError(error: Throwable)
    }

    interface Presenter : BasePresenter<View> {
        fun getWeather()
        fun selectWeatherDetail(detail: DailyForecastModel)
    }
}