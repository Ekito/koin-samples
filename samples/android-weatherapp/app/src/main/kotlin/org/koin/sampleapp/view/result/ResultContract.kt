package org.koin.sampleapp.view.result

import org.koin.sampleapp.model.DailyForecastModel
import org.koin.sampleapp.util.mvp.BasePresenter
import org.koin.sampleapp.util.mvp.BaseView

/**
 * Weather MVP Contract
 */
interface ResultContract {
    interface View : BaseView<Presenter> {
        fun onDetailSaved(id : String)
        fun displayError(error: Throwable)
    }

    interface Presenter : BasePresenter<View> {
        fun selectWeatherDetail(detail: DailyForecastModel)
    }
}
