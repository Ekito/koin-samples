package org.koin.sampleapp.view.result

import org.koin.sampleapp.model.DailyForecastModel
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.util.rx.SchedulerProvider
import org.koin.sampleapp.util.ext.with
import org.koin.sampleapp.view.AbstractPresenter

/**
 * Weather Presenter
 */
class ResultListPresenter(val weatherRepository: WeatherRepository, val schedulerProvider: SchedulerProvider, val weatherResultPresenter: ResultContract.Presenter, override var view: ResultListContract.View) : AbstractPresenter<ResultListContract.View, ResultListContract.Presenter>(), ResultListContract.Presenter {

    override fun getWeather() {
        launch {
            weatherRepository.getWeather()
                    .with(schedulerProvider)
                    .subscribe(
                            { weatherList -> view.displayWeather(weatherList) },
                            { error -> view.displayError(error) })
        }
    }

    override fun selectWeatherDetail(detail: DailyForecastModel) {
        launch {
            weatherRepository.getSelectedWeatherDetail(detail.id)
                    .with(schedulerProvider)
                    .subscribe({
                        weatherResultPresenter.selectWeatherDetail(detail)
                    }, { err ->
                        view.displayError(err)
                    })
        }
    }
}