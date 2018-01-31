package org.koin.sampleapp.view.detail

import org.koin.sampleapp.model.DailyForecastModel
import org.koin.sampleapp.util.mvp.BasePresenter
import org.koin.sampleapp.util.mvp.BaseView

interface WeatherDetailContract {
    interface View : BaseView<Presenter> {
        fun displayDetail(weather: DailyForecastModel)
    }

    interface Presenter : BasePresenter<View> {
        fun getDetail()
    }
}