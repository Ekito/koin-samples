package org.koin.sampleapp.view.result

import org.koin.sampleapp.model.DailyForecastModel
import org.koin.sampleapp.view.AbstractPresenter

/**
 * Weather Presenter
 */
class ResultPresenter() : AbstractPresenter<ResultContract.View, ResultContract.Presenter>(), ResultContract.Presenter {
    override fun selectWeatherDetail(detail: DailyForecastModel) {
        view.onDetailSaved(detail.id)
    }
}