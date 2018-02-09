package org.koin.sampleapp.view.weather

import org.koin.sampleapp.model.DailyForecastModel
import org.koin.sampleapp.view.AbstractPresenter

/**
 * Weather Presenter
 */
class WeatherResultPresenter() : AbstractPresenter<WeatherResultContract.View, WeatherResultContract.Presenter>(), WeatherResultContract.Presenter {
    override fun selectWeatherDetail(detail: DailyForecastModel) {
        view.onDetailSaved(detail.id)
    }
}