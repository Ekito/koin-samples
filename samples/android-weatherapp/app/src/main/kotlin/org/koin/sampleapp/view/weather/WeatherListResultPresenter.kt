package org.koin.sampleapp.view.weather

import org.koin.sampleapp.model.DailyForecastModel
import org.koin.sampleapp.repository.WeatherRepository
import org.koin.sampleapp.util.rx.SchedulerProvider
import org.koin.sampleapp.util.rx.with
import org.koin.sampleapp.view.AbstractPresenter

/**
 * Weather Presenter
 */
class WeatherListResultPresenter(val weatherRepository: WeatherRepository, val schedulerProvider: SchedulerProvider, val weatherResultPresenter: WeatherResultContract.Presenter) : AbstractPresenter<WeatherListResultContract.View, WeatherListResultContract.Presenter>(), WeatherListResultContract.Presenter {

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